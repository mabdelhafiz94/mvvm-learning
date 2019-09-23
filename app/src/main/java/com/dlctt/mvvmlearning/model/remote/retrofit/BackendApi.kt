package com.dlctt.mvvmlearning.model.remote.retrofit

import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single
import retrofit2.http.*

interface BackendApi {

    @GET("todos")
    fun getAllTasks(): Single<List<Task>>

}