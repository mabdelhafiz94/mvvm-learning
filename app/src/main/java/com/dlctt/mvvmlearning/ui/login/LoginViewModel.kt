package com.dlctt.mvvmlearning.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.local.UserSession
import com.dlctt.mvvmlearning.model.login.LoginDataSource
import com.dlctt.mvvmlearning.utils.BaseViewModel
import com.dlctt.mvvmlearning.utils.Event
import com.dlctt.mvvmlearning.utils.ServiceLocator
import com.dlctt.mvvmlearning.utils.parseException
import io.reactivex.SingleObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.launch

class LoginViewModel : BaseViewModel() {
    private val compositeDisposable: CompositeDisposable by lazy { CompositeDisposable() }

    private val loginRepo: LoginDataSource by lazy {
        ServiceLocator.getInstance().loginRepo
    }

    private val inputError = MutableLiveData<Event<String>>()
    private val navigateToMain = MutableLiveData<Event<Boolean>>()

    fun getInputError(): LiveData<Event<String>> = inputError

    fun navigateToMain(): LiveData<Event<Boolean>> = navigateToMain

    fun validateInput(userId: String) {
        if (userId.isEmpty()) {
            inputError.value = Event("please enter a user id")
            return
        }
        if (userId.toIntOrNull() == null) {
            inputError.value = Event("Invalid user id format")
            return
        }
        inputError.value = Event("ok")
        login(userId)
    }

    fun login(userId: String) {

        handleResult(Result.Loading())
        viewModelScope.launch {
            val result = loginRepo.loginById(userId.toInt())
            handleResult(result)
        }
/*.subscribe(object : SingleObserver<List<User>> {
            override fun onSuccess(usersList: List<User>) {
                val result: Result<List<User>> = if (usersList.isEmpty())
                    Result.Error(Exception("Wrong user id"))
                else
                    Result.Success(usersList)

                handleResult(result)
            }

            override fun onSubscribe(d: Disposable) {
                compositeDisposable.add(d)
                handleResult(Result.Loading())
            }

            override fun onError(e: Throwable) {
                handleResult(Result.Error(e))
            }
        })*/
    }

    private fun handleResult(result: Result<List<User>>) {
        loading.value = result is Result.Loading

        if (result is Result.Error)
            dialogMessage.value = Event(result.exception.parseException())

        if (result.isSucceeded()) {
            if (result.data!!.isEmpty()) {
                dialogMessage.value = Event("Wrong user id")
            } else {
                UserSession.userId = result.data[0].id
                toastMessage.value = Event("Logged in successfully as ${result.data[0].name}")
                navigateToMain.value = Event(true)
            }
        }
    }
}