package com.example.coins.Database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns


private const val SQL_CREATE_ENTRIES =
    "CREATE TABLE ${DatabaseContract.CoinEntry.TABLE_NAME} (" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY," +
            "${DatabaseContract.CoinEntry.COLUMN_NOMBRE} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_COUNTRY} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_REVIEW} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_IMG} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_AVAILABLE} TEXT," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUE_US} INT," +
            "${DatabaseContract.CoinEntry.COLUMN_YEAR} INT," +
            "${DatabaseContract.CoinEntry.COLUMN_VALUE} INT)"


private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${DatabaseContract.CoinEntry.TABLE_NAME}"


class Database(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    // TODO(8) Este método se ejecuta cuando es necesario crear el esquema de base de datos.
    // TODO(8.1) Este recibe como parámetro una instancia de SQLiteDatabase y que permite ejecutar SQL de DDL.
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES) // TODO (9) Ejecuta el DDL.
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // TODO (10) Cuando la base de datos ya existe y se define una nueva versión, se deben ejecutar scripts de migración.
        db.execSQL(SQL_DELETE_ENTRIES) // Como esta base de datos se utilizará como caché, no nos interesa conservar los datos almacenados en la versión anterior.
        onCreate(db) // Se vuelve a crear la base.
    }

    // TODO(11) Se definen en constantes, el nombre de la base de datos y la versión
    companion object {
        const val DATABASE_NAME = "Coin.db"
        const val DATABASE_VERSION = 1
    }
}