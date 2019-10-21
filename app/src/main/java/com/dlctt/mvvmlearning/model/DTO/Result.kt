package com.dlctt.mvvmlearning.model.DTO

sealed class Result<T>(
    val data: T? = null,
    val exception: Exception? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(exception: Exception) : Result<T>(null, exception)
    class Loading : Result<Nothing>()

    fun isSucceeded(): Boolean {
        return this is Success && data != null
    }

}