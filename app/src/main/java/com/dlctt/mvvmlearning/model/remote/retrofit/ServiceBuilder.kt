package com.dlctt.mvvmlearning.model.remote.retrofit

import com.dlctt.mvvmlearning.model.remote.retrofit.interceptors.LoggingInterceptor
import com.dlctt.mvvmlearning.utils.Constants
import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ServiceBuilder {

    private val httpClient = OkHttpClient.Builder()
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .connectTimeout(1, TimeUnit.MINUTES)
        .addInterceptor(LoggingInterceptor())

    private var retrofitBuilder: Retrofit.Builder? = null

    private fun initRetrofitBuilder(gson: Gson) {

        retrofitBuilder = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    }

    fun <S> buildService(serviceClass: Class<S>, gson: Gson): S {

        initRetrofitBuilder(gson)
        val retrofit = retrofitBuilder!!.client(httpClient.build()).build()
        return retrofit.create(serviceClass)
    }

}
