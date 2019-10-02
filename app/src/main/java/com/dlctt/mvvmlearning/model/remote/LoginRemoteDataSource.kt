package com.dlctt.mvvmlearning.model.remote

import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.login.LoginDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.LoginApi
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class LoginRemoteDataSource(private val loginApi: LoginApi) :
    LoginDataSource {
    override fun loginById(id: Int): Single<List<User>> {
        return loginApi.loginById(id)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}