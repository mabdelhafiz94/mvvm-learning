package com.dlctt.mvvmlearning.model.remote.retrofit

import com.dlctt.mvvmlearning.model.DTO.User
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginApi {

    @GET("users")
    fun loginById(@Query("id") id: Int): Single<List<User>>
}