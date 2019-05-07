package com.antipov.coroutines.idp.ui.main.di

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.repository.StocksRepository
import com.antipov.coroutines.idp.data.repository.impl.StocksRepositoryImpl
import com.antipov.coroutines.idp.data.retrofit.ApiHelper
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.main.MainActivity
import com.antipov.coroutines.idp.ui.main.MainPresenter
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule {

    @Provides
    @MainScreenScope
    fun providePresenter(repository: StocksRepository) = MainPresenter(repository)

    @Provides
    @MainScreenScope
    fun provideMainActivityNavigator(mainActivity: MainActivity) = AppNavigator(mainActivity, R.id.content)

    @Provides
    @MainScreenScope
    fun provideStocksRepository(apiHelper: ApiHelper) : StocksRepository = StocksRepositoryImpl(apiHelper)
}