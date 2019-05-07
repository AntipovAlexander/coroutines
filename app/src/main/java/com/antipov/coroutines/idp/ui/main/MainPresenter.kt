package com.antipov.coroutines.idp.ui.main

import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.concurrent.CancellationException

@UseExperimental(ObsoleteCoroutinesApi::class)
@InjectViewState
class MainPresenter(private val repository: StocksRepository) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()

        val tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 0)

        launch {
            for (event in tickerChannel) {
                Timber.d("Tick!")
            }
        }

        launch(compositeJob) {
            try {
                val data = repository.getStocksAsync().await()
                data.toString()
            } catch (t: CancellationException) {
                Timber.d("Cancelled!! 1")
            }
        }
    }

}