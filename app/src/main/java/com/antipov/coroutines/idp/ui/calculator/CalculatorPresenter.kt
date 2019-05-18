package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.launch
import timber.log.Timber

@InjectViewState
class CalculatorPresenter(private val repository: StocksRepository) : BasePresenter<CalculatorView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch {
            val data = repository.getAllStocksInDbAsync()
            Timber.d(data.toString())
        }
    }

}