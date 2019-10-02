package com.dlctt.mvvmlearning.model.DTO

import com.dlctt.mvvmlearning.utils.Event

sealed class Resource<T>(
    val data: T? = null,
    val message: Event<String>? = null

) {
    class Success<T>(data: T) : Resource<T>(data)
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: Event<String>, data: T? = null) : Resource<T>(data, message)
}