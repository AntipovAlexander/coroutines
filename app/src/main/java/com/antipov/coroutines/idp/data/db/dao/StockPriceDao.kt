package com.antipov.coroutines.idp.data.db.dao

import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.CLOSE_COLUMN
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.DATE_COLUMN
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.HIGH_COLUMN
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.LOW_COLUMN
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.OPEN_COLUMN
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper.Companion.TABLE_NAME
import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.channels.Channel
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

/**
 * CRUD
 */
class StockPriceDao(private val helper: StockPriceDbHelper) {

    private var stockUpdatesChannel = Channel<StockPrice>()

    fun create(stockPrice: StockPrice) {
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

    fun getAll(): MutableList<StockPrice> {
        val list = mutableListOf<StockPrice>()
        helper.use {
            select(TABLE_NAME)
                .parseList(object : MapRowParser<MutableList<StockPrice>> {
                    override fun parseRow(columns: Map<String, Any?>): MutableList<StockPrice> {
                        with(columns) {
                            val stockDate = getValue(DATE_COLUMN).toString()
                            val open = getValue(OPEN_COLUMN).toString().toFloat()
                            val high = getValue(HIGH_COLUMN).toString().toFloat()
                            val close = getValue(CLOSE_COLUMN).toString().toFloat()
                            val low = getValue(LOW_COLUMN).toString().toFloat()
                            val dataObj = StockPrice.Data(open, high, low, close)
                            list.add(StockPrice(stockDate, dataObj))
                        }
                        return list
                    }
                })
        }
        return list
    }

    fun getFirstItem(): StockPrice {
        return helper.use {
            select(TABLE_NAME)
                .limit(1)
                .parseSingle(object : MapRowParser<StockPrice> {
                    override fun parseRow(columns: Map<String, Any?>): StockPrice {
                        with(columns) {
                            val stockDate = getValue(DATE_COLUMN).toString()
                            val open = getValue(OPEN_COLUMN).toString().toFloat()
                            val high = getValue(HIGH_COLUMN).toString().toFloat()
                            val low = getValue(LOW_COLUMN).toString().toFloat()
                            val close = getValue(CLOSE_COLUMN).toString().toFloat()
                            val dataObj = StockPrice.Data(open, high, low, close)
                            return StockPrice(stockDate, dataObj)
                        }
                    }
                })
        }
    }

    fun dropAll() {
        helper.use { execSQL("delete from $TABLE_NAME") }
    }

    fun getUpdatesChannel(): Channel<StockPrice> = stockUpdatesChannel
}