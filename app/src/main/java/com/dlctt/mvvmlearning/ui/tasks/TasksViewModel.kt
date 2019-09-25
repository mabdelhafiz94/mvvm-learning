package com.dlctt.mvvmlearning.ui.tasks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
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

    private val tasksLiveData: MutableLiveData<List<Task>> by lazy { MutableLiveData<List<Task>>() }

    fun loadTasks(): LiveData<List<Task>> {
        tasksRepo.getTasks().subscribe(object : SingleObserver<List<Task>> {
            override fun onSuccess(t: List<Task>) {
                tasksLiveData.value = t
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                Log.e("onError", e.message)
                tasksLiveData.value = emptyList()
            }
        })

        return tasksLiveData
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}