package com.antipov.coroutines.idp.ui.base

import android.support.annotation.StringRes
import com.arellomobile.mvp.MvpView

interface BaseView : MvpView {
    fun showProgress() { /* implement if needed */
    }

    fun hideProgress() { /* implement if needed */
    }

    fun showMessage(message: String) { /* implement if needed */
    }

    fun showMessage(@StringRes message: Int) { /* implement if needed */
    }

    fun showFullScreenError(message: String) { /* implement if needed */
    }

    fun hideFullScreenError() { /* implement if needed */
    }

    fun onNoInternet() { /* implement if needed */
    }
}