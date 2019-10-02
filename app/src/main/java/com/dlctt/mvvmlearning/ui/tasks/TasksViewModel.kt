package com.dlctt.mvvmlearning.ui.tasks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dlctt.mvvmlearning.model.DTO.Resource
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.parseException
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by abdelhafiz on 9/25/19.
 */
class TasksViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val tasksRepo: TasksDataSource by lazy {
        ServiceLocator.getInstance().tasksRepo
    }

    private val resourceLiveData: MutableLiveData<Resource<List<Task>>> by lazy {
        MutableLiveData<Resource<List<Task>>>().also { it.value = Resource.Loading() }
    }

    fun loadTasks(userId: Int) {

        val singleObserver: Single<List<Task>> = if (userId == 0)
            tasksRepo.getTasks()
        else
            tasksRepo.getTasksByUserId(userId)

        singleObserver.subscribe(object : SingleObserver<List<Task>> {
            override fun onSuccess(t: List<Task>) {
                resourceLiveData.value = Resource.Success(t)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                resourceLiveData.value = Resource.Loading()
            }

            override fun onError(e: Throwable) {
                resourceLiveData.value = Resource.Error(parseException(e))
            }
        })
    }

    fun getResourceLiveData(): LiveData<Resource<List<Task>>> = resourceLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}