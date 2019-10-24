package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single

class TasksRepo(private val tasksRemoteDataSource: TasksDataSource) :
    TasksDataSource {
    private var online: Boolean = true

    override fun getTasks(): Single<List<Task>> {

        return if (online)
            tasksRemoteDataSource.getTasks()
        else
            Single.just(ArrayList())
    }

    override suspend fun getTasksByUserId(userId: Int): List<Task> {

        return if (online)
            tasksRemoteDataSource.getTasksByUserId(userId)
        else
            emptyList()
    }
}