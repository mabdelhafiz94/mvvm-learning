package com.dlctt.mvvmlearning.ui.tasks

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.handleError
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

    private val tasksLiveData: MutableLiveData<List<Task>> = MutableLiveData()

    private val errorMsgLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>().also {
            it.value = String()
        }
    }
    private val isLoadingLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>().also { it.value = false }
    }

    init {
        loadTasks()
    }

    private fun loadTasks() {
        tasksRepo.getTasks().subscribe(object : SingleObserver<List<Task>> {
            override fun onSuccess(t: List<Task>) {
                isLoadingLiveData.value = false
                tasksLiveData.value = t
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                isLoadingLiveData.value = true
            }

            override fun onError(e: Throwable) {
                isLoadingLiveData.value = false
                errorMsgLiveData.value = handleError(e)
                tasksLiveData.value = emptyList()
            }
        })
    }

    fun getTasksLiveData(): LiveData<List<Task>> {
        return tasksLiveData
    }

    fun getErrorMsgLiveData(): LiveData<String> {
        return errorMsgLiveData
    }

    fun isLoadingLiveData(): LiveData<Boolean> {
        return isLoadingLiveData
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}