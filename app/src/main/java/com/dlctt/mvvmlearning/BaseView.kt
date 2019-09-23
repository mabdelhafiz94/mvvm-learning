package com.dlctt.mvvmlearning

/**
 * Created by abdelhafiz on 05/07/18.
 */
interface BaseView {

    fun showLoading(show: Boolean)

    fun showDialogMessage(message: String)
}
