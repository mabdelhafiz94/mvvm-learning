package com.dlctt.mvvmlearning.utils

import android.content.Context
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.model.TasksRepo
import com.dlctt.mvvmlearning.model.remote.TasksRemoteDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.BackendApi
import com.dlctt.mvvmlearning.model.remote.retrofit.ServiceBuilder
import com.google.gson.Gson
import com.google.gson.GsonBuilder

class ServiceLocator private constructor() {

    private val backendApi: BackendApi by lazy {
        ServiceBuilder.buildService(
            BackendApi::class.java,
            jsonFactory
        )
    }
    private val jsonFactory: Gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }
    private val tasksRemoteDataSource: TasksDataSource by lazy {
        TasksRemoteDataSource(backendApi)
    }

    private val tasksRepo: TasksDataSource by lazy {
        TasksRepo(tasksRemoteDataSource)
    }


    companion object {
        @Volatile
        private var instance: ServiceLocator? = null

        @JvmStatic
        fun getServiceLocator(): ServiceLocator {
            if (instance == null)
                throw UnImplementedException("ServiceLocator must be initialized first using init method.")

            return instance as ServiceLocator
        }

        @JvmStatic
        fun init() {
            if (instance == null)
                synchronized(this)
                {
                    instance = ServiceLocator()
                }
        }
    }

}