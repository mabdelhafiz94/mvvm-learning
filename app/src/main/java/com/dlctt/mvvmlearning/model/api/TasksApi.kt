package com.dlctt.mvvmlearning.model.api

import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single
import retrofit2.http.*

interface TasksApi {

    @GET("todos")
    fun getAllTasks(): Single<List<Task>>

    @GET("todos")
    suspend fun getTasksByUserId(@Query("userId") userId: Int): List<Task>

}