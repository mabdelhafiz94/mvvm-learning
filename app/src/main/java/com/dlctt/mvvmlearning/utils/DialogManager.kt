package com.dlctt.mvvmlearning.utils

import android.content.Context
import android.content.DialogInterface
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast

import com.dlctt.mvvmlearning.R

object DialogManager {
    fun showDialog(context: Context?, msgStrId: Int) {
        if (context != null) {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(R.string.app_name)
            dialog.setMessage(msgStrId)
            dialog.setPositiveButton(R.string.ok, null)
            dialog.show()
        }
    }

    fun showDialog(context: Context?, msg: String) {
        if (context != null) {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(R.string.app_name)
            dialog.setMessage(msg)
            dialog.setPositiveButton(R.string.ok, null)
            dialog.show()
        }
    }

    fun showDialog(context: Context?, msg: String, listener: DialogInterface.OnClickListener) {
        if (context != null) {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle(R.string.app_name)
            dialog.setMessage(msg)
            dialog.setCancelable(false)
            dialog.setPositiveButton(R.string.ok, listener)
            dialog.show()
        }
    }

    fun showSnackError(v: View, msgStrId: Int) {
        Snackbar.make(v, msgStrId, Snackbar.LENGTH_LONG).show()
    }

    fun showDialog(
        context: Context?, msg: String, positiveBtn: String, negativeBtn: String,
        cancelable: Boolean,
        title: String,
        positiveListener: DialogInterface.OnClickListener?,
        negativeListener: DialogInterface.OnClickListener?
    ) {
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

    fun showToast(context: Context?, message: String) {
        if (context != null)
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }
}
