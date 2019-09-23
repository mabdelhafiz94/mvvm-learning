package com.dlctt.mvvmlearning

import android.app.Application
import com.dlctt.mvvmlearning.utils.ServiceLocator

class MVVMLearningApp : Application() {

    override fun onCreate() {
        super.onCreate()
        ServiceLocator.init()
    }

}