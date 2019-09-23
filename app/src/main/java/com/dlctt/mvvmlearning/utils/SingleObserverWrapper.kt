package com.dlctt.mvvmlearning.utils

import com.dlctt.mvvmlearning.BaseView
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * Created by abdelhafiz on 9/23/19.
 */
abstract class SingleObserverWrapper<T>(
    private val baseView: BaseView,
    private val disposables: CompositeDisposable
) : SingleObserver<T> {
    override fun onSuccess(t: T) {
        baseView.showLoading(false)
        onComplete(t)
    }

    override fun onSubscribe(d: Disposable) {
        disposables.add(d)
        baseView.showLoading(true)
    }

    override fun onError(e: Throwable) {
        baseView.showLoading(false)
        baseView.showDialogMessage(e.message ?: "Unknown Error: $e")
    }

    abstract fun onComplete(t: T)
}