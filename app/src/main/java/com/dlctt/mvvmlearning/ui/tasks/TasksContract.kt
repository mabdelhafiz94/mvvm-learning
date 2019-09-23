package com.dlctt.mvvmlearning.ui.tasks

import com.dlctt.mvvmlearning.BasePresenter
import com.dlctt.mvvmlearning.BaseView
import com.dlctt.mvvmlearning.model.DTO.Task

interface TasksContract {

    interface View : BaseView {

        fun onTasksLoaded(tasks: List<Task>)
    }

    interface Presenter : BasePresenter {

        fun loadTasks()
    }
}