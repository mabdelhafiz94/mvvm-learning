package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.utils.navigateToFragment

class TasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        navigateToFragment(TasksFragment(), true)
    }
}
