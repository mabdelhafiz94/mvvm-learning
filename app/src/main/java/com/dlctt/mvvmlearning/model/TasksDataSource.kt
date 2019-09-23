package com.dlctt.mvvmlearning.model

import com.dlctt.mvvmlearning.model.DTO.Task
import io.reactivex.Single

interface TasksDataSource {

    fun getTasks(): Single<List<Task>>

}