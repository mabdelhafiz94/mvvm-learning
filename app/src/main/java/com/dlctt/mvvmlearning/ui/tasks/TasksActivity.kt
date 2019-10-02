package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.utils.Constants
import com.dlctt.mvvmlearning.utils.navigateToFragment

class TasksActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        if (savedInstanceState == null) {
            val bundle = Bundle().also {
                it.putInt(
                    Constants.USER_ID,
                    intent.getIntExtra(Constants.USER_ID, 0)
                )
            }
            TasksFragment().also {
                it.arguments = bundle
                navigateToFragment(it, true, R.id.fragments_main_container)
            }
        }
    }
}
