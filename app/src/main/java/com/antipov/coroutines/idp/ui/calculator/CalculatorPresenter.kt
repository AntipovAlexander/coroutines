package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState

@InjectViewState
class CalculatorPresenter(repository: StocksRepository) : BasePresenter<CalculatorView>() {
}