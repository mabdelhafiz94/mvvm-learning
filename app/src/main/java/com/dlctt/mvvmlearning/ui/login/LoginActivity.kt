package com.dlctt.mvvmlearning.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.utils.navigateToFragment

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState == null)
            navigateToFragment(LoginFragment(), true, R.id.login_fragments_container)
    }
}
