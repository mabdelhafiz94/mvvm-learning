package com.dlctt.mvvmlearning.utils

import com.dlctt.mvvmlearning.model.tasks.TasksDataSource
import com.dlctt.mvvmlearning.model.tasks.TasksRepo
import com.dlctt.mvvmlearning.model.login.LoginDataSource
import com.dlctt.mvvmlearning.model.login.LoginRepo
import com.dlctt.mvvmlearning.model.remote.TasksRemoteDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.LoginApi
import com.dlctt.mvvmlearning.model.remote.retrofit.ServiceBuilder
import com.dlctt.mvvmlearning.model.remote.retrofit.TasksApi
import com.dlctt.mvvmlearning.model.remote.LoginRemoteDataSource
import com.google.gson.Gson
import com.google.gson.GsonBuilder

/*
* Singleton implementation done this way to allow arguments to be passed through constructor
* and init method
*/
class ServiceLocator private constructor() {

    private val tasksApi: TasksApi by lazy {
        ServiceBuilder.buildService(
            TasksApi::class.java,
            jsonFactory
        )
    }

    private val loginApi: LoginApi by lazy {
        ServiceBuilder.buildService(
            LoginApi::class.java,
            jsonFactory
        )
    }

    private val jsonFactory: Gson by lazy {
        GsonBuilder()
            .setPrettyPrinting()
            .create()
    }

    private val tasksRemoteDataSource: TasksDataSource by lazy {
        TasksRemoteDataSource(tasksApi)
    }

    private val loginRemoteDataSource: LoginDataSource by lazy {
        LoginRemoteDataSource(loginApi)
    }

    val tasksRepo: TasksDataSource by lazy {
        TasksRepo(tasksRemoteDataSource)
    }

    val loginRepo: LoginDataSource by lazy {
        LoginRepo(loginRemoteDataSource)
    }


    companion object {
        @Volatile
        private var instance: ServiceLocator? = null

        @JvmStatic
        fun getInstance(): ServiceLocator {
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