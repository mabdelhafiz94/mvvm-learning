package com.dlctt.mvvmlearning.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.MutableLiveData
import com.dlctt.mvvmlearning.model.DTO.Resource
import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.LoginDataSource
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.parseException
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

class LoginViewModel : ViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val loginRepo: LoginDataSource by lazy {
        ServiceLocator.getInstance().loginRepo
    }

    private val loginResourceLiveData: MutableLiveData<Resource<List<User>>> by lazy {
        MutableLiveData<Resource<List<User>>>()
    }

    private val inputErrorLiveData: MutableLiveData<String> by lazy { MutableLiveData<String>() }

    fun login(userId: String) {
        if (userId.isEmpty()) {
            inputErrorLiveData.value = "please enter a user id"
            return
        }
        if (userId.toIntOrNull() == null) {
            inputErrorLiveData.value = "Invalid user id format"
            return
        }

        loginRepo.loginById(userId.toInt()).subscribe(object : SingleObserver<List<User>> {
            override fun onSuccess(usersList: List<User>) {
                if (usersList.isEmpty())
                    loginResourceLiveData.value = Resource.Error("Wrong user id")
                else
                    loginResourceLiveData.value = Resource.Success(usersList)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                loginResourceLiveData.value = Resource.Loading()
            }

            override fun onError(e: Throwable) {
                loginResourceLiveData.value = Resource.Error(parseException(e))
            }
        })
    }

    fun getInputErrorLiveData(): LiveData<String> = inputErrorLiveData

    fun getLoginResourceLiveData(): LiveData<Resource<List<User>>> = loginResourceLiveData
}