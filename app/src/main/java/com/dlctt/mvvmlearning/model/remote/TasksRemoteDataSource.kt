package com.dlctt.mvvmlearning.model.remote

import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.BackendApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class TasksRemoteDataSource(private val backendApi: BackendApi) : TasksDataSource {

    override fun getTasks(): Single<List<Task>> {
        return backendApi.getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}