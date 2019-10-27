package com.dlctt.mvvmlearning.model.DTO

sealed class Result<out T>(
    val data: T? = null,
    val exception: Throwable? = null
) {
    class Success<T>(data: T) : Result<T>(data)
    class Error<T>(exception: Throwable) : Result<T>(null, exception)
    class Loading : Result<Nothing>()

    fun isSucceeded(): Boolean {
        return this is Success && data != null
    }

}

inline fun <T> tryCatch(block: () -> Result<T>): Result<T> {
    return try {
        block()
    } catch (ex: Exception) {
        Result.Error(ex)
    }
}
