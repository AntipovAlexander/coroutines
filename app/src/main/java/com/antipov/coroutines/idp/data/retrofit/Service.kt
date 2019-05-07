package com.antipov.coroutines.idp.data.retrofit

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.Deferred
import retrofit2.http.GET

interface Service {
    @GET("WIKI/AAPL/data.json?api_key=xw3sqcPrQ42gQnuv4sJQ&start_date=1980-12-12&end_date=1980-12-12")
    fun getStockForDayAsync(): Deferred<StockPrice>
}