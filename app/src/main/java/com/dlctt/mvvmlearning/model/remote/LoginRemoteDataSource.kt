package com.dlctt.mvvmlearning.model.remote

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.DTO.tryCatch
import com.dlctt.mvvmlearning.model.login.LoginDataSource
import com.dlctt.mvvmlearning.model.remote.retrofit.LoginApi
import com.dlctt.mvvmlearning.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRemoteDataSource(private val loginApi: LoginApi) : LoginDataSource {
    override suspend fun loginById(id: Int): Result<List<User>> {
        return tryCatch {
            withContext(Dispatchers.IO) {
                val list = loginApi.loginById(id)
                return@withContext if (list.isEmpty()) {
                    Result.Error(Exception(Constants.WRONG_USER_ID_MSG))
                } else {
                    Result.Success(list)
                }
            }
        }
    }
}