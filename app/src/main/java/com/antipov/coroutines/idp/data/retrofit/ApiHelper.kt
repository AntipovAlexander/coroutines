package com.antipov.coroutines.idp.data.retrofit

class ApiHelper(private val apiService: Service) {
    fun getStocksAsync() = apiService.getStockForDayAsync()
}