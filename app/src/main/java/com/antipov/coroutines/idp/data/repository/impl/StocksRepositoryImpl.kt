package com.antipov.coroutines.idp.data.repository.impl

import com.antipov.coroutines.idp.data.db.dao.StockPriceDao
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.data.retrofit.ApiHelper

class StocksRepositoryImpl(
    private val apiHelper: ApiHelper,
    private val stockPriceDao: StockPriceDao
) : StocksRepository {

    override suspend fun saveStockToDb(stockPrice: StockPrice) = stockPriceDao.create(stockPrice)

    override fun getStocksAsync(day: String) = apiHelper.getStocksAsync(day)

    override fun dropAllStocksInDb() = stockPriceDao.dropAll()
}