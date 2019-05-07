package com.antipov.coroutines.idp.ui.main.di

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.data.repository.impl.StocksRepositoryImpl
import com.antipov.coroutines.idp.data.retrofit.ApiHelper
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.main.MainActivity
import com.antipov.coroutines.idp.ui.main.MainPresenter
import com.antipov.coroutines.idp.ui.main.di.qualifiers.EndDayCalendar
import com.antipov.coroutines.idp.ui.main.di.qualifiers.StartDayCalendar
import dagger.Module
import dagger.Provides
import java.text.SimpleDateFormat
import java.util.*

@Module
class MainScreenModule {

    private val startDayStr = "1980-12-12"
    private val endDayStr = "2018-03-27"

    @Provides
    @MainScreenScope
    fun providePresenter(
        repository: StocksRepository,
        @StartDayCalendar startDayCalendar: Calendar,
        @EndDayCalendar endDayCalendar: Calendar,
        dateFormat: SimpleDateFormat
    ) =
        MainPresenter(repository, startDayCalendar, endDayCalendar, dateFormat)

    @Provides
    @MainScreenScope
    fun provideMainActivityNavigator(mainActivity: MainActivity) = AppNavigator(mainActivity, R.id.content)

    @Provides
    @MainScreenScope
    fun provideStocksRepository(apiHelper: ApiHelper): StocksRepository = StocksRepositoryImpl(apiHelper)

    @Provides
    @MainScreenScope
    fun provideStartDayFormat() = SimpleDateFormat("yyyy-MM-dd")

    @Provides
    @MainScreenScope
    @StartDayCalendar
    fun provideStartDateCalendar(dateFormat: SimpleDateFormat): Calendar {
        return Calendar.getInstance().apply {
            time = dateFormat.parse(startDayStr)
        }
    }

    @Provides
    @MainScreenScope
    @EndDayCalendar
    fun provideEndDateCalendar(dateFormat: SimpleDateFormat): Calendar {
        return Calendar.getInstance().apply {
            time = dateFormat.parse(endDayStr)
        }
    }
}