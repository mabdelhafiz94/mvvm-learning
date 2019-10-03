package com.dlctt.mvvmlearning.ui.login

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.dlctt.mvvmlearning.model.DTO.Resource
import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.login.LoginDataSource
import com.dlctt.mvvmlearning.utils.Event
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

    private val inputErrorLiveData: MutableLiveData<Event<String>> by lazy { MutableLiveData<Event<String>>() }

    fun validateInput(userId: String): LiveData<Event<String>> {
        if (userId.isEmpty()) {
            inputErrorLiveData.value = Event("please enter a user id")
            return inputErrorLiveData
        }
        if (userId.toIntOrNull() == null) {
            inputErrorLiveData.value = Event("Invalid user id format")
            return inputErrorLiveData
        }
        inputErrorLiveData.value = null
        return inputErrorLiveData
    }

    fun login(userId: String): LiveData<Resource<List<User>>> {
        loginRepo.loginById(userId.toInt()).subscribe(object : SingleObserver<List<User>> {
            override fun onSuccess(usersList: List<User>) {
                if (usersList.isEmpty())
                    loginResourceLiveData.value = Resource.Error(Event("Wrong user id"))
                else
                    loginResourceLiveData.value = Resource.Success(usersList)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                loginResourceLiveData.value = Resource.Loading()
            }

            override fun onError(e: Throwable) {
                loginResourceLiveData.value = Resource.Error(Event(parseException(e)))
            }
        })

        return loginResourceLiveData
    }
}