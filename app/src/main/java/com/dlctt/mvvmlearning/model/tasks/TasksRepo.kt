package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task

class TasksRepo(private val tasksRemoteDataSource: TasksDataSource) :
    TasksDataSource {
    private var online: Boolean = true

    override suspend fun getTasks(): Result<List<Task>> {

        return if (online)
            tasksRemoteDataSource.getTasks()
        else
            Result.Success(ArrayList())
    }

    override suspend fun getTasksByUserId(userId: Int): Result<List<Task>> {

        return if (online)
            tasksRemoteDataSource.getTasksByUserId(userId)
        else
            Result.Success(emptyList())
    }
}