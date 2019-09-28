package com.dlctt.mvvmlearning.utils

import android.arch.lifecycle.ViewModel
import android.util.Log
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by abdelhafiz on 9/28/19.
 */
fun ViewModel.handleError(error: Throwable): String {
    val TAG = this.javaClass.simpleName
    when (error) {
        is HttpException -> return if (error.code() == 401 || error.code() == 404) {
            "Wrong Credentials"
        } else if (error.code() == 409) {
            "Server Conflict!"
        } else {
            val responseBody = error.response().errorBody()
            val errorJson = getErrorMessage(responseBody)
            Log.i(TAG, errorJson)
            "Generic server error"
        }
        is SocketTimeoutException -> return "Connection timed out"
        is IOException -> return "Error in internet connection"
        else -> {
            Log.e(TAG, error.message)
            return "Generic error"
        }
    }
}

private fun getErrorMessage(responseBody: ResponseBody?): String {
    return try {
        responseBody!!.string()
    } catch (e: Exception) {
        e.message ?: e.toString()
    }

}