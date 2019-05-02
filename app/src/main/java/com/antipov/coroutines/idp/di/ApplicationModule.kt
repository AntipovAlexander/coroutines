package com.antipov.coroutines.idp.di

import android.app.Application
import com.antipov.coroutines.idp.TestDependency
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule {

    @Provides
    @Singleton
    fun provideTestDependency(context: Application) = TestDependency()
}