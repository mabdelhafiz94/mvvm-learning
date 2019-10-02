package com.dlctt.mvvmlearning.model

import com.dlctt.mvvmlearning.model.DTO.User
import io.reactivex.Single

interface LoginDataSource {

    fun loginById(id: Int): Single<List<User>>
}