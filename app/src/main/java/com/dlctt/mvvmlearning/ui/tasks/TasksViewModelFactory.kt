package com.dlctt.mvvmlearning.ui.tasks

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class TasksViewModelFactory(private val userId: Int) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return TasksViewModel() as T
    }
}