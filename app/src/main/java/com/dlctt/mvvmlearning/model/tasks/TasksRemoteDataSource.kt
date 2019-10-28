package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.DTO.tryCatch
import com.dlctt.mvvmlearning.model.api.TasksApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksRemoteDataSource(private val tasksApi: TasksApi) : TasksDataSource {
    override suspend fun getTasks(): Result<List<Task>> {
        return tryCatch {
            withContext(Dispatchers.IO) {
                Result.Success(tasksApi.getAllTasks())
            }
        }
    }

    override suspend fun getTasksByUserId(userId: Int): Result<List<Task>> {
        return tryCatch {
            withContext(Dispatchers.IO) {
                Result.Success(tasksApi.getTasksByUserId(userId))
            }
        }
    }
}