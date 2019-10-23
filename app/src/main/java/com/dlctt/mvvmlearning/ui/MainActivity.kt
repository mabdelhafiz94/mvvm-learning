package com.dlctt.mvvmlearning.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.ui.tasks.TasksFragment
import com.dlctt.mvvmlearning.utils.navigateToFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            navigateToFragment(TasksFragment(), true, R.id.fragments_main_container)

        }
    }
}
