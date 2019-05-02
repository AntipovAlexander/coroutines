package com.antipov.coroutines.idp.utils.extensions

import android.app.Activity
import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.ContextCompat
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.TextView
import org.jetbrains.anko.*

fun Context.hideKeyboard(view: View?) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.windowToken, 0)
}

fun Context.showKeyboard(editText: EditText?) {
    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
}

fun Context.isNetworkAvailable(): Boolean {
    val info = connectivityManager.activeNetworkInfo
    return info != null && info.isConnected && !info.isRoaming
}

fun Fragment.isNetworkAvailable(): Boolean = activity?.isNetworkAvailable() ?: false

fun Context.dialCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}

fun Activity.showSnackbar(
    message: Int,
    length: Int = Snackbar.LENGTH_LONG,
    view: View = find(android.R.id.content)
): Snackbar =
    showIntSnackBar(this, view, message, length)

fun Activity.showSnackbar(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    parent: View = find(android.R.id.content)
): Snackbar =
    showStringSnackBar(this, parent, message, length)

fun Fragment.showSnackbar(
    message: Int,
    length: Int = Snackbar.LENGTH_LONG,
    view: View = activity!!.find(android.R.id.content)
): Snackbar =
    activity!!.showSnackbar(message, length, view)

fun Fragment.showSnackbar(
    message: String,
    length: Int = Snackbar.LENGTH_LONG,
    view: View = activity!!.find(android.R.id.content)
): Snackbar =
    activity!!.showSnackbar(message, length, view)

private fun showStringSnackBar(context: Context, parent: View, message: String, length: Int): Snackbar {
    val color = ContextCompat.getColor(context, android.R.color.white)
    val snackbar = Snackbar.make(parent, message, length)
    snackbar.view.setBackgroundColor(Color.RED)
    val textView = snackbar.view.find<TextView>(android.support.design.R.id.snackbar_text)
    textView.setTextColor(color)
    snackbar.show()

    return snackbar
}

private fun showIntSnackBar(context: Context, parent: View, message: Int, length: Int): Snackbar {
    val color = ContextCompat.getColor(context, android.R.color.white)
    val snackbar = Snackbar.make(parent, message, length)
    snackbar.view.setBackgroundColor(Color.RED)
    val textView = snackbar.view.find<TextView>(android.support.design.R.id.snackbar_text)
    textView.setTextColor(color)
    snackbar.show()

    return snackbar
}

inline fun <reified T : Activity> Context.enterActivityWithStackClearance() {
    startActivity(intentFor<T>().clearTask().newTask().clearTop())
}

inline fun <reified T : Activity> Activity.navigateUp(finishAction: () -> Unit) {
    if (isTaskRoot) {
        startActivity(intentFor<T>().clearTop())
        finishAction()
        return
    }
    finishAction()
}

fun Context.openMap(lat: Float? = null, long: Float? = null, title: String? = null) {

    val coordinates = if (lat != null && long != null) "geo:$lat,$long?" else "geo:0,0?"
    val textQuery = if (title == null) "" else "q=${Uri.encode(title, "UTF-8")}"
    val gmmIntentUri = Uri.parse("$coordinates$textQuery")
    var mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)

    // checking google maps is installed
    if (mapIntent.resolveActivity(packageManager) != null) {
        // if installed launch map activity
        mapIntent.setPackage("com.google.android.apps.maps")
    } else {
        // if not, launch browser
        mapIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("https://www.google.com/maps/search/?api=1&query=$coordinates")
        )
    }

    startActivity(mapIntent)
}

inline fun FragmentActivity.isAtLeastCreated(action: () -> Unit) {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.CREATED)) {
        action()
    }
}

inline fun FragmentActivity.isAtLeastStart(action: () -> Unit) {
    if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
        action()
    }
}

fun Activity.openEmailApp() {
    val intent = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:")
        putExtra(Intent.EXTRA_EMAIL, arrayOf("abc@xyz.com"))
    }
    if (intent.resolveActivity(packageManager) != null) {
        startActivity(intent)
    }
}

fun Activity.hideKeyboardOnLoseFocus(view: View? = find(android.R.id.content)) {
    if (view == null) return
    view.setOnTouchListener { _, _ ->
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            ?: return@setOnTouchListener false
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }

}
