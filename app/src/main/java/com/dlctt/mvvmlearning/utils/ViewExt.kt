package com.dlctt.mvvmlearning.utils

import android.support.design.widget.Snackbar
import android.view.View

fun View.showSnack(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_LONG).show()
}

fun View.show() {
    if (this.visibility != View.VISIBLE)
        this.visibility = View.VISIBLE
}

fun View.hide() {
    if (this.visibility != View.GONE)
        this.visibility = View.GONE
}