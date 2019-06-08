package com.antipov.coroutines.idp.ui.base

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.antipov.coroutines.idp.R
import com.antipov.coroutines.idp.navigation.AppNavigator
import com.antipov.coroutines.idp.utils.extensions.showSnackbar
import com.arellomobile.mvp.MvpAppCompatActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector, BaseView {

    @Inject
    lateinit var fragmentDispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    protected abstract val layoutRes: Int

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(layoutRes)
        initListeners()
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(getActivityNavigator())
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    /**
     * Overrides the pending Activity transition by performing the "Enter" animation.
     * Must be called onStart()
     */
    protected open fun overridePendingTransitionEnter() {
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

    /**
     * Overrides the pending Activity transition by performing the "Exit" animation.
     * Must be called on finish()
     */
    protected open fun overridePendingTransitionExit() {
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right)
    }

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent?, options: Bundle?) {
        super.startActivity(intent, options)
        overridePendingTransitionEnter()
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = fragmentDispatchingAndroidInjector

    abstract fun getActivityNavigator(): AppNavigator

    open fun initListeners() { /* implement if needed */
    }

    override fun showMessage(message: String) {
        showSnackbar(message)
    }

}
