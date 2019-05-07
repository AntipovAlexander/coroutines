package com.antipov.coroutines.idp.data.repository

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.Deferred

interface StocksRepository {
    fun getStocksAsync(day: String): Deferred<StockPrice>
}