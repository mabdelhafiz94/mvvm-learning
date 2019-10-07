package com.dlctt.mvvmlearning.utils

class Event<T>(private var content: T? = null) {
    private var consumed: Boolean = false

    fun getContent(): T? {
        if (consumed)
            content = null
        else
            consumed = true

        return content
    }

    fun peekContent(): T? {
        return if (consumed) null else content
    }
}