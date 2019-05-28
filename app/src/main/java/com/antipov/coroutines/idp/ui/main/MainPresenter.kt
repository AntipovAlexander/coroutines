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
import java.text.SimpleDateFormat
import java.util.*

@UseExperimental(ObsoleteCoroutinesApi::class)
@SuppressLint("SimpleDateFormat")
@InjectViewState
class MainPresenter(
    private val repository: StocksRepository,
    private val router: Router,
    private val startDayCalendar: Calendar,
    private val dateFormat: SimpleDateFormat
) : BasePresenter<MainView>() {

    lateinit var prevStockPrice: StockPrice

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        Timber.d(throwable)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        clearDb()
        subscribeUiToStockUpdates()
        scheduleStockUpdates()
    }

    private fun clearDb() = repository.dropAllStocksInDb()

    private fun subscribeUiToStockUpdates() =
        launch(exceptionHandler) {
            for (stock in repository.getStockChannel()) {
                launch(Dispatchers.Main) { viewState.updateUi(stock) }
            }
        }

    private fun scheduleStockUpdates() {
        val tickerChannel = ticker(delayMillis = 500, initialDelayMillis = 0)
        launch(exceptionHandler) {
            loop@ for (event in tickerChannel) {
                val stock = getStocksForNextDay()
                prevStockPrice = when {
                    stock.stockDate.isEmpty() -> continue@loop
                    !::prevStockPrice.isInitialized -> stock
                    else -> determineViewState(stock)
                }
                persistStock(stock)
            }
        }
    }

    private suspend fun persistStock(stock: StockPrice) {
        repository.saveStockToDb(stock)
    }

    private fun determineViewState(stock: StockPrice): StockPrice {
        launch(Dispatchers.Main) {
            if (stock.data.close > prevStockPrice.data.close) {
                viewState.setViewAsGrowth()
            } else {
                viewState.setViewAsDesc()
            }
        }
        return stock
    }

    private suspend fun getStocksForNextDay(): StockPrice {
        startDayCalendar.add(Calendar.DATE, 1)
        val formattedDay = dateFormat.format(startDayCalendar.timeInMillis)
        return repository.getStocksAsync(formattedDay).await()
    }

    fun openCalc() = router.navigateTo(Screens.Calculator)
}