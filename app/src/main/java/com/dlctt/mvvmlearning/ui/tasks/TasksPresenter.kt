package com.dlctt.mvvmlearning.ui.tasks

import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class TasksPresenter(private val view: TasksContract.View) : TasksContract.Presenter {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val tasksRepo: TasksDataSource by lazy {
        ServiceLocator.getServiceLocator().tasksRepo
    }


    override fun loadTasks() {
        tasksRepo.getTasks().subscribe(object : SingleObserver<List<Task>> {

            override fun onSuccess(t: List<Task>) {
                view.showLoading(false)
                view.onTasksLoaded(t)
            }

            override fun onSubscribe(d: Disposable) {
                view.showLoading(true)
                compositeDisposable.add(d)
            }

            override fun onError(e: Throwable) {
                view.showLoading(false)
                view.showDialogMessage(e.message ?: "Unknown Error: $e")
            }

        })
    }


    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}