package com.dlctt.mvvmlearning.ui.taskDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.model.tasks.TasksDataSource
import com.dlctt.mvvmlearning.utils.BaseViewModel
import com.dlctt.mvvmlearning.utils.Event
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.parseException
import kotlinx.coroutines.launch

/**
 * Created by abdelhafiz on 10/23/19.
 */
class TaskDetailsViewModel(private val taskId: Int) : BaseViewModel() {
    private val tasksRepo: TasksDataSource by lazy { ServiceLocator.getInstance().tasksRepo }
    private val taskDetailsLiveData = MutableLiveData<Task>()

    fun getTaskById() {
        if (newTask()) {
            handleResult(Result.Loading())
            viewModelScope.launch {
                val result = tasksRepo.getTasks()
                handleResult(result)
            }
        }
    }

    private fun newTask(): Boolean {
        val currTaskId = taskDetailsLiveData.value?.id ?: -1
        return (currTaskId == -1 || currTaskId != taskId)
    }

    private fun handleResult(result: Result<List<Task>>) {
        loading.value = result is Result.Loading

        if (result is Result.Error)
            dialogMessage.value = Event(result.exception.parseException())


        if (result is Result.Success && result.data != null) {
            try {
                taskDetailsLiveData.value =
                    result.data.first { task -> task.id == taskId }
            } catch (ex: NoSuchElementException) {
            }
        }
    }

    fun getTaskDetails(): LiveData<Task> = taskDetailsLiveData
}