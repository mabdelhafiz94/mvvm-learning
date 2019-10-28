package com.dlctt.mvvmlearning.model.api.interceptors

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

class LoggingInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        Log.i("OkHttp", request.toString())

        val t1 = System.nanoTime()
        Log.d(
            "OkHttp", String.format(
                "Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()
            )
        )

        val response = chain.proceed(request)

        Log.i("OkHttp", response.toString())

        Log.d("OkHttp", "Received response: $response")
        Log.i("response_code", response.code().toString() + "")
        val t2 = System.nanoTime()
        Log.d(
            "OkHttp", String.format(
                "Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6, response.headers()
            )
        )

        return response
    }
}