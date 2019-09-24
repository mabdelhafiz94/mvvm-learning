package com.dlctt.mvvmlearning.utils

interface ListItemCallback<T> {
    fun onItemClicked(item: T)
}