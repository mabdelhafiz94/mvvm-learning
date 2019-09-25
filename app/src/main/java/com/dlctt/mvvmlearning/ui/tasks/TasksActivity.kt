package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.dlctt.mvvmlearning.R

class TasksActivity : AppCompatActivity() {
    private var firstNavigateToFragment = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        navigateToFragment(TasksFragment())
    }

    private fun navigateToFragment(fragment: Fragment) {
        val tag = fragment.javaClass.simpleName

        if (!isFragmentVisible(tag)) {
            val fragmentManager = supportFragmentManager
            val fragmentTransaction = fragmentManager.beginTransaction()

            fragmentTransaction.setCustomAnimations(
                android.R.anim.fade_in,
                android.R.anim.fade_out, android.R.anim.fade_in, android.R.anim.fade_out
            )

            fragmentTransaction.replace(R.id.fragments_main_container, fragment, tag)

            if (!firstNavigateToFragment)
                fragmentTransaction.addToBackStack(tag)

            fragmentTransaction.commit()

            firstNavigateToFragment = false
        }
    }

    private fun isFragmentVisible(name: String): Boolean {
        val fragment = supportFragmentManager.findFragmentByTag(name)
        return fragment != null && fragment.isResumed
    }
}
