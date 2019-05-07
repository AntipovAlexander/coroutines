package com.antipov.coroutines.idp.data.retrofit

class ApiHelper(private val apiService: Service) {
    fun getStocksAsync(day: String) = apiService.getStockForDayAsync(day, day)
}