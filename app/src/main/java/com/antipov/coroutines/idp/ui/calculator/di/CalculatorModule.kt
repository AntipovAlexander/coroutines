package com.antipov.coroutines.idp.ui.calculator.di

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.db.dao.StockPriceDao
import com.antipov.coroutines.idp.data.db.helpers.StockPriceDbHelper
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.data.repository.impl.StocksRepositoryImpl
import com.antipov.coroutines.idp.data.retrofit.ApiHelper
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.calculator.CalculatorActivity
import com.antipov.coroutines.idp.ui.calculator.CalculatorInteractor
import com.antipov.coroutines.idp.ui.calculator.CalculatorPresenter
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat

@Module
class CalculatorModule {

    @Provides
    @CalculatorScope
    fun provideInteractor() = CalculatorInteractor()

    @Provides
    @CalculatorScope
    fun provideStartDayFormat() = SimpleDateFormat("yyyy-MM-dd")

    @Provides
    @CalculatorScope
    fun providePresenter(repository: StocksRepository, dateFormat: SimpleDateFormat, calculatorInteractor: CalculatorInteractor) =
        CalculatorPresenter(repository, dateFormat, calculatorInteractor)

    @Provides
    @CalculatorScope
    fun provideMainActivityNavigator(calculatorActivity: CalculatorActivity) =
        AppNavigator(calculatorActivity, R.id.content)

    @Provides
    @CalculatorScope
    fun provideStocksRepository(apiHelper: ApiHelper, stockPriceDbHelper: StockPriceDbHelper): StocksRepository =
        StocksRepositoryImpl(apiHelper, StockPriceDao(stockPriceDbHelper))

}