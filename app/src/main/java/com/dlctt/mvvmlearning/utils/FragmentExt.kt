package com.dlctt.mvvmlearning.utils

import android.content.DialogInterface
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Resource
import com.dlctt.mvvmlearning.model.DTO.Resource.Error
import com.dlctt.mvvmlearning.model.DTO.Resource.Loading

fun Fragment.showDialog(message: String?) {
    if (message == null) return
    val context = this.context
    if (context != null) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(R.string.app_name)
        dialog.setMessage(message)
        dialog.setPositiveButton(R.string.ok, null)
        dialog.show()
    }
}

fun Fragment.showDialog(msg: String, listener: DialogInterface.OnClickListener) {
    val context = this.context
    if (context != null) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(R.string.app_name)
        dialog.setMessage(msg)
        dialog.setCancelable(false)
        dialog.setPositiveButton(R.string.ok, listener)
        dialog.show()
    }
}

fun Fragment.showDialog(
    msg: String, positiveBtn: String, negativeBtn: String, cancelable: Boolean,
    title: String, positiveListener: DialogInterface.OnClickListener?,
    negativeListener: DialogInterface.OnClickListener?
) {
    val context = this.context
    if (context != null) {
        val dialog = AlertDialog.Builder(context)
        dialog.setTitle(title)
        dialog.setMessage(msg)
        dialog.setPositiveButton(positiveBtn, positiveListener)
        dialog.setNegativeButton(negativeBtn, negativeListener)
        dialog.setCancelable(cancelable)
        dialog.show()
    }
}

fun Fragment.showToast(message: String?) {
    if (message == null) return

    val context = this.context
    if (context != null)
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

fun Fragment.hideKeyboard() {
    val activity = activity
    if (activity != null) {
        (activity as AppCompatActivity).hideKeyboard()
    }
}

fun Fragment.statusBarColorToSolid(colorResId: Int) {
    val activity = activity
    if (activity != null) {
        (activity as AppCompatActivity).statusBarColorToSolid(colorResId)
    }
}

fun Fragment.statusBarColorToSolidWhite() {
    val activity = activity
    if (activity != null) {
        (activity as AppCompatActivity).statusBarColorToSolidWhite()
    }
}

fun <T> Fragment.handleUIState(
    resource: Resource<T>?,
    loadingIndicator: View,
    useToastForMsgs: Boolean
) {
    if (resource is Loading)
        loadingIndicator.show()
    else
        loadingIndicator.hide()

    if (resource is Error) {
        if (useToastForMsgs)
            showToast(resource.message?.getContent())
        else
            showDialog(resource.message?.getContent())
    }
}