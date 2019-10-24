package com.dlctt.mvvmlearning.model.remote

import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.tasks.TasksDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.TasksApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksRemoteDataSource(private val tasksApi: TasksApi) :
    TasksDataSource {
    override fun getTasks(): Single<List<Task>> {
        return tasksApi.getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getTasksByUserId(userId: Int): List<Task> {
        return withContext(Dispatchers.IO)
        {
            tasksApi.getTasksByUserId(userId)
        }
    }
}