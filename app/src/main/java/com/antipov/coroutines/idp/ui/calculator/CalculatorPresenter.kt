package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.antipov.coroutines.idp.utils.extensions.runOnUi
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch

@InjectViewState
class CalculatorPresenter(
    private val repository: StocksRepository,
    private val calculatorInteractor: CalculatorInteractor
) : BasePresenter<CalculatorView>(), CalculatorInteractor.Callback {

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> viewState.onError() }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch(exceptionHandler) {
            val data = repository.getAllStocksInDb()
            updateUi(data)
            calculatorInteractor.calculate(data, this@CalculatorPresenter)
        }
    }

    private fun updateUi(data: MutableList<StockPrice>) {
        runOnUi {
            viewState.setDates(data.first().stockDate, data.last().stockDate)
            viewState.setProgressMax(data.size)
        }
    }

    override fun incrementProgress() {
        runOnUi {
            viewState.incrementProgress()
        }
    }

    override fun onCalculationsFinished(price: StockPrice) {
        runOnUi {
            viewState.seResult(price)
        }
    }

}