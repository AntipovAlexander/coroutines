package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.base.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.android.synthetic.main.activity_calculator.*
import javax.inject.Inject

class CalculatorActivity : BaseActivity(), CalculatorView {

    @Inject
    @InjectPresenter
    lateinit var presenter: CalculatorPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    lateinit var navigator: AppNavigator

    override val layoutRes = R.layout.activity_calculator

    override fun getActivityNavigator() = navigator

    override fun setDates(dateStart: String, dateEnd: String) {
        dateTextView.text = getString(R.string.star_end_date_format, dateStart, dateEnd)
    }

    override fun setProgressMax(size: Int) {
        progressBar.max = size
        progressBar.progress = 0
    }

    override fun incrementProgress() {
        progressBar.progress++
    }

    override fun seResult(price: StockPrice) {
        result.text = getString(R.string.result_format, price.data.close, price.stockDate)
    }
}
