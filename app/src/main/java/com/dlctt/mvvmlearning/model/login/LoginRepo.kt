package com.dlctt.mvvmlearning.model.login

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.User

class LoginRepo(private val loginRemoteDataSource: LoginDataSource) :
    LoginDataSource {

    override suspend fun loginById(id: Int): Result<List<User>> {
        return loginRemoteDataSource.loginById(id)
    }
}