package com.antipov.coroutines.idp.ui.main

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

@InjectViewState
class MainPresenter(private val repository: StocksRepository) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        launch(compositeJob) {
            try {
                val data = repository.getStocksAsync().await()
                delay(10000)
                data.toString()
            } catch (t: CancellationException) {
                Timber.d("Cancelled!! 1")
            }
        }
    }

}