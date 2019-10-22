package com.dlctt.mvvmlearning.utils

import android.content.DialogInterface
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Result.Error
import com.dlctt.mvvmlearning.model.DTO.Result.Loading

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
    result: Result<T>?,
    loadingIndicator: View,
    useToastForMsgs: Boolean
) {
    if (result is Loading)
        loadingIndicator.show()
    else
        loadingIndicator.hide()

    if (result is Error) {
        if (useToastForMsgs)
            showToast(result.exception?.message)
        else
            showDialog(result.exception?.message)
    }
}

fun Fragment.observeMessages(useToast: Boolean, messageLiveData: LiveData<Event<String>>) {
    messageLiveData.observe(viewLifecycleOwner, Observer { event ->
        event?.let {
            if (event.peekContent() != null)
                if (useToast)
                    showToast(event.getContent())
                else
                    showDialog(event.getContent())
        }
    })
}