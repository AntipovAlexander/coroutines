package com.antipov.coroutines.idp.ui.calculator

import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.ui.base.BaseActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import javax.inject.Inject

class CalculatorActivity : BaseActivity(), CalculatorView {

    @Inject
    @InjectPresenter
    lateinit var presenter: CalculatorPresenter

    @ProvidePresenter
    fun providePresenter() = presenter

    @Inject
    lateinit var navigator: AppNavigator

    override val layoutRes = R.layout.activity_calculator

    override fun getActivityNavigator() = navigator
}
