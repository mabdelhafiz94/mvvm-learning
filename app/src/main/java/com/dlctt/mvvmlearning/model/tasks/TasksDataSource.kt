package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single

interface TasksDataSource {
    suspend fun getTasks(): Result<List<Task>>

    suspend fun getTasksByUserId(userId: Int): Result<List<Task>>

}