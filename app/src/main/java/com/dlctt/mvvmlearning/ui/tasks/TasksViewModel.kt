package com.dlctt.mvvmlearning.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.tasks.TasksDataSource
import com.dlctt.mvvmlearning.utils.Event
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.parseException
import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by abdelhafiz on 9/25/19.
 */
class TasksViewModel(private val userId: Int) : ViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val tasksRepo: TasksDataSource by lazy {
        ServiceLocator.getInstance().tasksRepo
    }

    private val resultLiveData: MutableLiveData<Result<List<Task>>> by lazy {
        MutableLiveData<Result<List<Task>>>().also { it.value = Result.Loading() }
    }

    init {
        loadTasks()
    }

    private fun loadTasks() {

        val singleObserver: Single<List<Task>> = if (userId == 0)
            tasksRepo.getTasks()
        else
            tasksRepo.getTasksByUserId(userId)

        singleObserver.subscribe(object : SingleObserver<List<Task>> {
            override fun onSuccess(t: List<Task>) {
                resultLiveData.value = Result.Success(t)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                resultLiveData.value = Result.Loading()
            }

            override fun onError(e: Throwable) {
                resultLiveData.value = Result.Error(Event(parseException(e)))
            }
        })
    }

    fun getResourceLiveData(): LiveData<Result<List<Task>>> = resultLiveData

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}