package com.antipov.coroutines.idp.ui.main

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.base.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    lateinit var navigator: AppNavigator

    override val layoutRes = R.layout.activity_main

    override fun getActivityNavigator() = navigator

    override fun updateUi(stock: StockPrice) {
        stockDateValue.text = getString(R.string.stock_date, stock.stockDate)
        openValue.text = getString(R.string.open, stock.data.open.toString())
        highValue.text = getString(R.string.high, stock.data.high.toString())
        lowValue.text = getString(R.string.low, stock.data.low.toString())
        closeValue.text = getString(R.string.close, stock.data.close.toString())
    }

    override fun setViewAsGrowth() {
        arrow.setImageResource(R.drawable.ic_arrow_growth)
    }

    override fun setViewAsDesc() {
        arrow.setImageResource(R.drawable.ic_arrow_desc)
    }

}
