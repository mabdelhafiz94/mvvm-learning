package com.dlctt.mvvmlearning.model.login

import com.dlctt.mvvmlearning.model.DTO.Result
import com.dlctt.mvvmlearning.model.DTO.User

interface LoginDataSource {
    suspend fun loginById(id: Int): Result<List<User>>
}