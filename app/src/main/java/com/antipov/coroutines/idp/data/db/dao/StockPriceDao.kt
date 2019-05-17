package com.antipov.coroutines.idp.data.db.dao

import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.TABLE_NAME
import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.channels.Channel
import org.jetbrains.anko.db.insert

/**
 * CRUD
 */
class StockPriceDao(private val helper: StockPriceDbHelper) {

    private var stockUpdatesChannel = Channel<StockPrice>()

    fun create(stockPrice: StockPrice) {
        with(StockPriceDbHelper) {
            helper.use {
                insert(
                    TABLE_NAME,
                    DATE_COLUMN to stockPrice.stockDate,
                    OPEN_COLUMN to stockPrice.data.open,
                    HIGH_COLUMN to stockPrice.data.high,
                    LOW_COLUMN to stockPrice.data.low,
                    CLOSE_COLUMN to stockPrice.data.close
                )
                stockUpdatesChannel.offer(stockPrice)
            }
        }
    }

    fun dropAll() {
        helper.use { execSQL("delete from $TABLE_NAME") }
    }

    fun getUpdatesChannel(): Channel<StockPrice> = stockUpdatesChannel
}