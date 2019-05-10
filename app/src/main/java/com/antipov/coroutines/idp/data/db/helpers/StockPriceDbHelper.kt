package com.antipov.coroutines.idp.data.db.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*

class StockPriceDbHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, 1) {
    init {
        instance = this
    }

    companion object {
        const val DB_NAME = "stock_data"
        const val TABLE_NAME = "StockPrice"
        const val DATE_COLUMN = "date"
        const val OPEN_COLUMN = "open"
        const val HIGH_COLUMN = "high"
        const val LOW_COLUMN = "low"
        const val CLOSE_COLUMN = "close"

        private var instance: StockPriceDbHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance
            ?: StockPriceDbHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(
            "StockPrice", true,
            "id" to INTEGER + PRIMARY_KEY + UNIQUE,
            DATE_COLUMN to TEXT,
            OPEN_COLUMN to REAL,
            HIGH_COLUMN to REAL,
            LOW_COLUMN to REAL,
            CLOSE_COLUMN to REAL
        )
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable("User", true)
    }
}