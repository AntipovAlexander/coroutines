package com.antipov.coroutines.idp.ui.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView

abstract class BasePresenter<V : MvpView> : MvpPresenter<V>() {

    // todo: implement CompositeJob

//    private val compositeDisposable = CompositeDisposable()
//
//    override fun onDestroy() {
//        compositeDisposable.dispose()
//    }
//
//    protected fun Disposable.addToDisposables() {
//        compositeDisposable.add(this)
//    }
}