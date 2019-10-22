package com.dlctt.mvvmlearning.utils

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

open class BaseViewModel : ViewModel() {
    val loading = MutableLiveData<Boolean>()
    val dialogMessage = MutableLiveData<Event<String>>()
    val toastMessage = MutableLiveData<Event<String>>()

    fun isLoading(): LiveData<Boolean> = loading

    fun getDialogMessage(): LiveData<Event<String>> = dialogMessage

    fun getToastMessage(): LiveData<Event<String>> = toastMessage
}