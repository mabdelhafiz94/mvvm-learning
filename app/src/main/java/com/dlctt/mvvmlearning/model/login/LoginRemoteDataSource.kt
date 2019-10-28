package com.dlctt.mvvmlearning.model.login

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.User
import com.dlctt.mvvmlearning.model.DTO.tryCatch
import com.dlctt.mvvmlearning.model.api.LoginApi
import com.dlctt.mvvmlearning.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class LoginRemoteDataSource(private val loginApi: LoginApi) : LoginDataSource {
    override suspend fun loginById(id: Int): Result<List<User>> {
        return tryCatch {
            withContext(Dispatchers.IO) {
                val usersList = loginApi.loginById(id)
                return@withContext if (usersList.isEmpty()) {
                    Result.Error(Exception(Constants.WRONG_USER_ID_MSG))
                } else {
                    Result.Success(usersList)
                }
            }
        }
    }
}