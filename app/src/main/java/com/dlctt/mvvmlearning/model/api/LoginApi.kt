package com.dlctt.mvvmlearning.model.api

import com.dlctt.mvvmlearning.model.DTO.User
import retrofit2.http.GET
import retrofit2.http.Query


interface LoginApi {
    @GET("users")
    suspend fun loginById(@Query("id") id: Int): List<User>
}