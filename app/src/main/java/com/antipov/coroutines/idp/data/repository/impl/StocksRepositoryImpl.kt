package com.antipov.coroutines.idp.data.repository.impl

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.data.retrofit.ApiHelper

class StocksRepositoryImpl(private val apiHelper: ApiHelper) : StocksRepository {
    override fun getStocksAsync() = apiHelper.getStocksAsync()
}