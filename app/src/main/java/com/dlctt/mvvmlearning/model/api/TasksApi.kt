package com.dlctt.mvvmlearning.model.api

import com.dlctt.mvvmlearning.model.DTO.Task
import retrofit2.http.GET
import retrofit2.http.Query

interface TasksApi {

    @GET("todos")
    suspend fun getAllTasks(): List<Task>

    @GET("todos")
    suspend fun getTasksByUserId(@Query("userId") userId: Int): List<Task>

}