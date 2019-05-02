package com.antipov.coroutines.idp.ui.main.di

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.main.MainActivity
import com.antipov.coroutines.idp.ui.main.MainPresenter
import com.antipov.coroutines.idp.ui.main.di.MainScreenScope
import dagger.Module
import dagger.Provides

@Module
class MainScreenModule {

    @Provides
    @MainScreenScope
    fun providePresenter() = MainPresenter()

    @Provides
    @MainScreenScope
    fun provideMainActivityNavigator(mainActivity: MainActivity) = AppNavigator(mainActivity, R.id.content)
}