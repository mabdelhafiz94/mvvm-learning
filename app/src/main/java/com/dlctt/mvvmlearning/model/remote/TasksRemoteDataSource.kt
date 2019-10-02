package com.dlctt.mvvmlearning.model.remote

import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.TasksApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TasksRemoteDataSource(private val tasksApi: TasksApi) : TasksDataSource {
    override fun getTasks(): Single<List<Task>> {
        return tasksApi.getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTasksByUserId(userId: Int): Single<List<Task>> {
        return tasksApi.getTasksByUserId(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}