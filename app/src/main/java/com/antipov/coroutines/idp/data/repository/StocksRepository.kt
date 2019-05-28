package com.antipov.coroutines.idp.data.repository

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.channels.Channel

interface StocksRepository {
    fun getStocksAsync(day: String): Deferred<StockPrice>
    suspend fun getAllStocksInDbAsync(): MutableList<StockPrice>
    suspend fun getFirstStock(): StockPrice
    fun dropAllStocksInDb()
    suspend fun saveStockToDb(stockPrice: StockPrice)
    fun getStockChannel(): Channel<StockPrice>
}