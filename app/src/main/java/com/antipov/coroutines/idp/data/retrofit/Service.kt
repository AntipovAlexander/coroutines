package com.antipov.coroutines.idp.data.retrofit

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.Deferred
import retrofit2.http.GET
import retrofit2.http.Query

interface Service {
    // todo: fix interceptors
    @GET("WIKI/AAPL/data.json?api_key=xw3sqcPrQ42gQnuv4sJQ")
    fun getStockForDayAsync(
        @Query("start_date") start: String,
        @Query("end_date") end: String
    ): Deferred<StockPrice>
}