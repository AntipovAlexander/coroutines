package com.antipov.coroutines.idp.ui.main

import android.annotation.SuppressLint
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

@UseExperimental(ObsoleteCoroutinesApi::class)
@SuppressLint("SimpleDateFormat")
@InjectViewState
class MainPresenter(
    private val repository: StocksRepository,
    private val startDayCalendar: Calendar,
    private val endDayCalendar: Calendar,
    private val dateFormat: SimpleDateFormat
) : BasePresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        val stockUpdatesChannel = repository.getStockChannel()
        launch {
            for (update in stockUpdatesChannel) {
                Timber.d(update.stockDate)
            }
        }
        val tickerChannel = ticker(delayMillis = 1_000, initialDelayMillis = 0)
        launch {
            for (event in tickerChannel) {
                startDayCalendar.add(Calendar.DATE, 1)
                val formattedDay = dateFormat.format(startDayCalendar.timeInMillis)
                val stock = repository.getStocksAsync(formattedDay).await()
                repository.saveStockToDb(stock)
            }
        }
    }

    override fun onDestroy() {
        repository.dropAllStocksInDb()
        super.onDestroy()
    }

}