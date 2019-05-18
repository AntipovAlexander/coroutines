package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat

@InjectViewState
class CalculatorPresenter(
    private val repository: StocksRepository,
    private val dateFormat: SimpleDateFormat,
    private val calculatorInteractor: CalculatorInteractor
) : BasePresenter<CalculatorView>(), CalculatorInteractor.Callback {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch {
            val data = repository.getAllStocksInDbAsync()
            launch(Dispatchers.Main) {
                viewState.setDates(data.first().stockDate, data.last().stockDate)
                viewState.setProgressMax(data.size)
            }
            calculatorInteractor.calculate(data, this@CalculatorPresenter)
        }
    }

    override fun incrementProgress() {
        launch(Dispatchers.Main) {
            viewState.incrementProgress()
        }
    }

    override fun onCalculationsFinished(price: StockPrice) {
        launch(Dispatchers.Main) {
            viewState.seResult(price)
        }
    }

}