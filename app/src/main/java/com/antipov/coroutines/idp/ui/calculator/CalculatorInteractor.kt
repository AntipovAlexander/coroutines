package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.model.StockPrice
import kotlinx.coroutines.delay

class CalculatorInteractor {

    suspend fun calculate(list: MutableList<StockPrice>, callback: Callback) {
        var max = list.first()
        list.forEach { stock ->
            if (stock.data.close > max.data.close) {
                max = stock
            }
            delay((Math.random() * 10).toLong() * 100L)
            callback.incrementProgress()
        }
        callback.onCalculationsFinished(max)
    }

    interface Callback {
        fun incrementProgress()
        fun onCalculationsFinished(price: StockPrice)
    }
}