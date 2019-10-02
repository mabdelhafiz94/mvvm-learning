package com.dlctt.mvvmlearning.model.tasks

import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single

interface TasksDataSource {

    fun getTasks(): Single<List<Task>>

    fun getTasksByUserId(userId: Int): Single<List<Task>>

}