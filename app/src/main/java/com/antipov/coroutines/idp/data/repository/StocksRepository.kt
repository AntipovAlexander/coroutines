package com.antipov.coroutines.idp.data.repository

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel

interface StocksRepository {
    fun getStocksAsync(day: String): Deferred<StockPrice>
    suspend fun getAllStocksInDb(): MutableList<StockPrice>
    suspend fun getFirstStock(): StockPrice
    suspend fun saveStockToDb(stockPrice: StockPrice)
    fun dropAllStocksInDb()
    fun getStockChannel(): Channel<StockPrice>
    fun getTickerChannel(): ReceiveChannel<Unit>
}