package com.dlctt.mvvmlearning.ui.tasks

import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.TasksDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.SingleObserverWrapper
import io.reactivex.disposables.CompositeDisposable

class TasksPresenter(private val view: TasksContract.View) : TasksContract.Presenter {

    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val tasksRepo: TasksDataSource by lazy {
        ServiceLocator.getInstance().tasksRepo
    }

    override fun loadTasks() {
        tasksRepo.getTasks()
            .subscribe(object : SingleObserverWrapper<List<Task>>(view, compositeDisposable) {
                override fun onComplete(t: List<Task>) {
                    view.showLoading(false)
                    view.onTasksLoaded(t)
                }

            })
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}