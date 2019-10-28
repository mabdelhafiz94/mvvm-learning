package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.DTO.tryCatch
import com.dlctt.mvvmlearning.model.api.TasksApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TasksRemoteDataSource(private val tasksApi: TasksApi) : TasksDataSource {
    override fun getTasks(): Single<List<Task>> {
        return tasksApi.getAllTasks()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

    override suspend fun getTasksByUserId(userId: Int): Result<List<Task>> {
        return tryCatch {
            withContext(Dispatchers.IO) {
                Result.Success(tasksApi.getTasksByUserId(userId))
            }
        }
    }
}