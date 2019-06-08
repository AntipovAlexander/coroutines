package com.antipov.coroutines.idp.ui.main

import android.annotation.SuppressLint
import com.antipov.coroutines.idp.data.model.StockPrice
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.navigation.Screens
import com.antipov.coroutines.idp.ui.base.BasePresenter
import com.antipov.coroutines.idp.utils.extensions.runOnUi
import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import ru.terrakok.cicerone.Router
import java.text.SimpleDateFormat
import java.util.*

@SuppressLint("SimpleDateFormat")
@InjectViewState
class MainPresenter(
    private val repository: StocksRepository,
    private val router: Router,
    private val startDayCalendar: Calendar,
    private val dateFormat: SimpleDateFormat
) : BasePresenter<MainView>() {

    private lateinit var prevStockPrice: StockPrice

    private val exceptionHandler = CoroutineExceptionHandler { _, _ -> viewState.onError() }

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
                runOnUi { viewState.updateUi(stock) }
            }
        }

    private fun scheduleStockUpdates() =
        launch(exceptionHandler) {
            loop@ for (event in repository.getTickerChannel()) {
                val stock = getStocksForNextDay()
                prevStockPrice = when {
                    stock.stockDate.isEmpty() -> continue@loop
                    !::prevStockPrice.isInitialized -> stock
                    else -> determineViewState(stock)
                }
                persistStock(stock)
            }
        }

    private suspend fun persistStock(stock: StockPrice) = repository.saveStockToDb(stock)

    private fun determineViewState(stock: StockPrice): StockPrice {
        when (stock.data.close > prevStockPrice.data.close) {
            true -> runOnUi { viewState.setViewAsGrowth() }
            false -> runOnUi { viewState.setViewAsDesc() }
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