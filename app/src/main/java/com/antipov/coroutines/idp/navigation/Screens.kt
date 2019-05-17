package com.antipov.coroutines.idp.navigation

import android.content.Context
import android.content.Intent
import com.antipov.coroutines.idp.ui.calculator.CalculatorActivity
import org.jetbrains.anko.intentFor
import ru.terrakok.cicerone.android.support.SupportAppScreen

/**
 * Put there your screens.
 */

class Screens {

    object Calculator : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent {
            return context.intentFor<CalculatorActivity>()
        }
    }

}