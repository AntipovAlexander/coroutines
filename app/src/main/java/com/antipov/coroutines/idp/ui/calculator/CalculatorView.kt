package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.ui.base.BaseView

interface CalculatorView : BaseView {
    fun setDates(dateStart: String, dateEnd: String)
    fun setProgressMax(size: Int)
    fun incrementProgress()
    fun seResult(price: StockPrice)
}