package com.dlctt.mvvmlearning.model.login

import com.dlctt.mvvmlearning.model.DTO.User
import io.reactivex.Single

class LoginRepo(private val loginRemoteDataSource: LoginDataSource) :
    LoginDataSource {
    private var online: Boolean = true

    override fun loginById(id: Int): Single<List<User>> {
        return if (online)
            loginRemoteDataSource.loginById(id)
        else
            Single.just(emptyList())
    }
}