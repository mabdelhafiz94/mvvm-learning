package com.dlctt.mvvmlearning.ui.tasks

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.local.LocalDataSource
import com.dlctt.mvvmlearning.model.local.UserSession
import com.dlctt.mvvmlearning.model.tasks.TasksDataSource
import com.dlctt.mvvmlearning.utils.BaseViewModel
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
class TasksViewModel : BaseViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }
    private val userSession: UserSession = UserSession

    private val tasksRepo: TasksDataSource by lazy { ServiceLocator.getInstance().tasksRepo }
    private val tasksLiveData = MutableLiveData<List<Task>>()
    private val navigateToTaskDetails = MutableLiveData<Event<Boolean>>()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        val singleObserver: Single<List<Task>> = if (userSession.userId == 0)
            tasksRepo.getTasks()
        else
            tasksRepo.getTasksByUserId(userSession.userId)

        singleObserver.subscribe(object : SingleObserver<List<Task>> {
            override fun onSuccess(t: List<Task>) {
                handleResult(Result.Success(t))
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                handleResult(Result.Loading())
            }

            override fun onError(e: Throwable) {
                handleResult(Result.Error(e))
            }
        })
    }

    private fun handleResult(result: Result<List<Task>>) {
        loading.value = result is Result.Loading

        if (result is Result.Error)
            dialogMessage.value = Event(result.exception.parseException())


        if (result is Result.Success && result.data != null)
            tasksLiveData.value = result.data
    }

    fun getTasksLiveData(): LiveData<List<Task>> = tasksLiveData

    fun navigateToTaskDetails(): LiveData<Event<Boolean>> = navigateToTaskDetails

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }

    fun onTaskSelected(item: Task) {
        LocalDataSource.selectedTaskId = item.id
        navigateToTaskDetails.value = Event(true)
    }
}