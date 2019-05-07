package com.antipov.coroutines.idp.ui.main

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@InjectViewState
class MainPresenter(private val repository: StocksRepository) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        GlobalScope.launch {
            val data = repository.getStocksAsync().await()
            data.toString()
        }
    }

}