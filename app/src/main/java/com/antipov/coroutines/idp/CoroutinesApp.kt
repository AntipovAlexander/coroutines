package com.antipov.coroutines.idp

import android.app.Activity
import android.app.Application
import com.antipov.coroutines.idp.BuildConfig.DEBUG
import com.antipov.coroutines.idp.di.DaggerApplicationComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class CoroutinesApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityDispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector() = activityDispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        if (DEBUG) Timber.plant(Timber.DebugTree())
        DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
            .inject(this)
    }
}