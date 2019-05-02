package com.antipov.coroutines.idp.navigation

import android.support.annotation.IdRes
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import com.antipov.coroutines.idp.R
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

abstract class BaseNavigator(activity: AppCompatActivity, @IdRes layout: Int) : SupportAppNavigator(activity, layout) {

    override fun setupFragmentTransaction(
        command: Command?,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction
    ) {
        fragmentTransaction.setCustomAnimations(
            R.anim.slide_from_right,
            R.anim.slide_to_left,
            R.anim.slide_from_left,
            R.anim.slide_to_right
        )
        super.setupFragmentTransaction(command, currentFragment, nextFragment, fragmentTransaction)
    }
}