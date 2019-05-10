package com.antipov.coroutines.idp.data.db.dao

import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper
import com.antipov.coroutines.idp.data.model.StockPrice
import org.jetbrains.anko.db.dropTable
import org.jetbrains.anko.db.insert

/**
 * CRUD
 */
class StockPriceDao(private val helper: StockPriceDbHelper) {

    fun create(stockPrice: StockPrice) {
        with(StockPriceDbHelper) {
            helper.use {
                insert(
                    TABLE_NAME,
                    DATE_COLUMN to stockPrice.stockDate,
                    OPEN_COLUMN to stockPrice.data?.open,
                    HIGH_COLUMN to stockPrice.data?.high,
                    LOW_COLUMN to stockPrice.data?.low,
                    CLOSE_COLUMN to stockPrice.data?.close
                )
            }
        }
    }

    fun dropAll() {
        helper.use {
            dropTable(StockPriceDbHelper.TABLE_NAME, true)
        }
    }
}