package com.antipov.coroutines.idp.ui.main

import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.ui.base.BaseView

interface MainView : BaseView {
    fun updateUi(stock: StockPrice)
    fun setViewAsGrowth()
    fun setViewAsDesc()
}