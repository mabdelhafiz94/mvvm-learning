package com.dlctt.mvvmlearning.utils

import android.util.Log
import okhttp3.ResponseBody
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

/**
 * Created by abdelhafiz on 9/28/19.
 */
fun Throwable?.parseException(): String {
    if (this == null) return "Generic Error"
    val TAG = this.javaClass.simpleName
    when (this) {
        is HttpException -> return if (this.code() == 401 || this.code() == 404) {
            "Wrong Credentials"
        } else if (this.code() == 409) {
            "Server Conflict!"
        } else {
            val responseBody = this.response()?.errorBody()
            val errorJson = getErrorMessage(responseBody)
            Log.i(TAG, errorJson)
            "Generic server error"
        }
        is SocketTimeoutException -> return "Connection timed out"
        is IOException -> return "Error in internet connection"
        else -> {
            Log.e(TAG, this.message)
            return this.message ?: "Generic error"
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