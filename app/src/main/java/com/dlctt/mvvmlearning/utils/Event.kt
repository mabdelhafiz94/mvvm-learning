package com.dlctt.mvvmlearning.utils

class Event<T>(private var data: T? = null) {
    private var consumed: Boolean = false

    fun getData(): T? {
        return if (!consumed) {
            consumed = true
            data
        } else
            null
    }
}