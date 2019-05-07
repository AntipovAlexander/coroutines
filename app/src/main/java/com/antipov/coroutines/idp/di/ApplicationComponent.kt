package com.antipov.coroutines.idp.di

import android.app.Application
import com.antipov.coroutines.idp.CoroutinesApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        ApplicationModule::class,
        DataModule::class,
        ActivityBindingModule::class,
        NavigationModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: CoroutinesApp)
}