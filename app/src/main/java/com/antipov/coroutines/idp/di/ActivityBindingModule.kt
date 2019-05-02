package com.antipov.coroutines.idp.di

import com.antipov.coroutines.idp.ui.main.MainActivity
import com.antipov.coroutines.idp.ui.main.di.MainScreenModule
import com.antipov.coroutines.idp.ui.main.di.MainScreenScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @MainScreenScope
    @ContributesAndroidInjector(modules = [MainScreenModule::class])
    abstract fun bindLiveTicker(): MainActivity
}