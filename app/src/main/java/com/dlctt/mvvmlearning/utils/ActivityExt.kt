package com.dlctt.mvvmlearning.utils

import android.app.Activity
import android.content.DialogInterface
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.dlctt.mvvmlearning.R

fun AppCompatActivity.showDialog(message: String) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(R.string.app_name)
    dialog.setMessage(message)
    dialog.setPositiveButton(R.string.ok, null)
    dialog.show()
}

fun AppCompatActivity.showDialog(msg: String, listener: DialogInterface.OnClickListener) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(R.string.app_name)
    dialog.setMessage(msg)
    dialog.setCancelable(false)
    dialog.setPositiveButton(R.string.ok, listener)
    dialog.show()
}

fun AppCompatActivity.showDialog(
    msg: String, positiveBtn: String, negativeBtn: String, cancelable: Boolean,
    title: String, positiveListener: DialogInterface.OnClickListener?,
    negativeListener: DialogInterface.OnClickListener?
) {
    val dialog = AlertDialog.Builder(this)
    dialog.setTitle(title)
    dialog.setMessage(msg)
    dialog.setPositiveButton(positiveBtn, positiveListener)
    dialog.setNegativeButton(negativeBtn, negativeListener)
    dialog.setCancelable(cancelable)
    dialog.show()
}

fun AppCompatActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun AppCompatActivity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager?
    //Find the currently focused view, so we can grab the correct window token from it.
    var view = this.currentFocus
    //If no view currently has focus, create a new one, just so we can grab a window token from it
    if (view == null) {
        view = View(this)
    }
    imm?.hideSoftInputFromWindow(view.windowToken, 0)
}

fun AppCompatActivity.statusBarColorToGradient(colorResId: Int) {
    val background = ContextCompat.getDrawable(this, colorResId)
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
    window.setBackgroundDrawable(background)
}

fun AppCompatActivity.statusBarColorToSolid(colorResId: Int) {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
    window.statusBarColor = ContextCompat.getColor(this, colorResId)
    val decor = window.decorView
    decor.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
}

fun AppCompatActivity.statusBarColorToSolidWhite() {
    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.transparent)
    window.navigationBarColor = ContextCompat.getColor(this, android.R.color.black)
    window.statusBarColor = ContextCompat.getColor(this, android.R.color.white)
    val decorView = window.decorView
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}

fun AppCompatActivity.navigateToFragment(
    fragment: Fragment,
    firstNavigateToFragment: Boolean,
    viewContainerId: Int
) {
    val tag = fragment.javaClass.simpleName

    if (!isFragmentVisible(tag)) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

//        fragmentTransaction.setCustomAnimations(
//            android.R.anim.fade_in,
//            android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out
//        )

        fragmentTransaction.replace(viewContainerId, fragment, tag)

        if (!firstNavigateToFragment)
            fragmentTransaction.addToBackStack(tag)

        fragmentTransaction.commit()
    }
}

fun AppCompatActivity.isFragmentVisible(name: String): Boolean {
    val fragment = supportFragmentManager.findFragmentByTag(name)
    return fragment != null && fragment.isResumed
}
