package com.dlctt.mvvmlearning.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dlctt.mvvmlearning.model.DTO.Result
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

    private val loginResultLiveData: MutableLiveData<Event<Result<List<User>>>> by lazy {
        MutableLiveData<Event<Result<List<User>>>>()
    }

    private val inputErrorLiveData: MutableLiveData<Event<String>> by lazy { MutableLiveData<Event<String>>() }

    fun getInputErrorLiveData(): LiveData<Event<String>> = inputErrorLiveData

    fun validateInput(userId: String) {
        if (userId.isEmpty()) {
            inputErrorLiveData.value = Event("please enter a user id")
            return
        }
        if (userId.toIntOrNull() == null) {
            inputErrorLiveData.value = Event("Invalid user id format")
            return
        }
        inputErrorLiveData.value = Event(null)
    }

    fun login(userId: String): LiveData<Event<Result<List<User>>>> {
        loginRepo.loginById(userId.toInt()).subscribe(object : SingleObserver<List<User>> {
            override fun onSuccess(usersList: List<User>) {
                if (usersList.isEmpty())
                    loginResultLiveData.value = Event(Result.Error(Event("Wrong user id")))
                else
                    loginResultLiveData.value = Event(Result.Success(usersList))
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                loginResultLiveData.value = Event(Result.Loading())
            }

            override fun onError(e: Throwable) {
                loginResultLiveData.value = Event(Result.Error(Event(parseException(e))))
            }
        })

        return loginResultLiveData
    }
}