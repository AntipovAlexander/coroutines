package com.antipov.coroutines.idp.ui.main

import android.annotation.SuppressLint
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.navigation.Screens
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import timber.log.Timber
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.*

@UseExperimental(ObsoleteCoroutinesApi::class)
@SuppressLint("SimpleDateFormat")
@InjectViewState
class MainPresenter(
    private val repository: StocksRepository,
    private val router: Router,
    private val startDayCalendar: Calendar,
    private val endDayCalendar: Calendar,
    private val dateFormat: SimpleDateFormat
) : BasePresenter<MainView>() {

    lateinit var prevStockPrice: StockPrice

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        repository.dropAllStocksInDb()
        val stockUpdatesChannel = repository.getStockChannel()
        launch(Dispatchers.Main) {
            for (stock in stockUpdatesChannel) {
                viewState.updateUi(stock)
            }
        }
        val tickerChannel = ticker(delayMillis = 500, initialDelayMillis = 0)
        launch(exceptionHandler) {
            loop@ for (event in tickerChannel) {
                startDayCalendar.add(Calendar.DATE, 1)
                val formattedDay = dateFormat.format(startDayCalendar.timeInMillis)
                val stock = repository.getStocksAsync(formattedDay).await()
                if (stock.stockDate.isEmpty()) continue@loop
                if (!::prevStockPrice.isInitialized) {
                    prevStockPrice = stock
                } else {
                    launch(Dispatchers.Main) {
                        if (stock.data.close > prevStockPrice.data.close) {
                            viewState.setViewAsGrowth()
                        } else {
                            viewState.setViewAsDesc()
                        }
                        prevStockPrice = stock
                    }
                }
                repository.saveStockToDb(stock)
            }
        }
    }

    fun openCalc() {
        router.navigateTo(Screens.Calculator)
    }
}