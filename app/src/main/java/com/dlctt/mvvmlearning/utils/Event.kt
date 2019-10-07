package com.dlctt.mvvmlearning.utils

class Event<T>(private var content: T? = null) {
    private var consumed: Boolean = false

    fun getContent(): T? {
        return if (!consumed) {
            consumed = true
            content
        } else
            null
    }
}