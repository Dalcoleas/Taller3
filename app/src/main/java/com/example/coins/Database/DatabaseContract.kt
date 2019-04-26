package com.example.coins.Database

import android.provider.BaseColumns


object DatabaseContract {

    object CoinEntry : BaseColumns { // Se guardan los datos relevantes de la tabla, como su nombre y sus campos.

        const val TABLE_NAME = "coin"

        // Se crea una constante por cada columna de la tabla.
        const val COLUMN_ID = "_id"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_COUNTRY = "country"
        const val COLUMN_VALUE = "value"
        const val COLUMN_VALUE_US = "value_us"
        const val COLUMN_YEAR = "year"
        const val COLUMN_REVIEW = "review"
        const val COLUMN_AVAILABLE = "available"
        const val COLUMN_IMG = "img"

    }
}
