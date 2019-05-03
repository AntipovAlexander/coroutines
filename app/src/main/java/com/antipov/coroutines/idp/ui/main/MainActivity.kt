package com.antipov.coroutines.idp.ui.main

import android.os.Bundle
import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.data.retrofit.RetrofitFactory
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.base.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainActivity : BaseActivity(), MainView {

    @Inject
    @InjectPresenter
    lateinit var presenter: MainPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    lateinit var navigator: AppNavigator

    override val layoutRes = R.layout.activity_main

    override fun getActivityNavigator() = navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GlobalScope.launch {
            val result = RetrofitFactory.makeRetrofitService().getStockForDay().await()
            result.toString()
        }
    }
}
