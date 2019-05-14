package com.example.coins.Models

import android.os.Parcel
import android.os.Parcelable

data class Coin(
    val _id: String = "N/A",
    val nombre: String = "N/A",
    val country: String = "N/A",
    var value: Int = 0,
    val value_us: Int = 0,
    var year: Int = 2019,
    var review: String = "N/A",
    var available: Boolean = true,
    var img: String = "N/A"
) : Parcelable {
    constructor(source: Parcel) : this(
        source.readString(),
        source.readString(),
        source.readString(),
        source.readInt(),
        source.readInt(),
        source.readInt(),
        source.readString(),
        1 == source.readInt(),
        source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(_id)
        writeString(nombre)
        writeString(country)
        writeInt(value)
        writeInt(value_us)
        writeInt(year)
        writeString(review)
        writeInt((if (available) 1 else 0))
        writeString(img)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Coin> = object : Parcelable.Creator<Coin> {
            override fun createFromParcel(source: Parcel): Coin = Coin(source)
            override fun newArray(size: Int): Array<Coin?> = arrayOfNulls(size)
        }
    }
}




