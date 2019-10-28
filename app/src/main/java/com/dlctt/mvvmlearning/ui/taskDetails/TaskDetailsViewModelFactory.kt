package com.dlctt.mvvmlearning.ui.taskDetails

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class TaskDetailsViewModelFactory(private val taskId: Int) :
    ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TaskDetailsViewModel(taskId) as T
    }
}