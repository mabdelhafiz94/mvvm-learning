package com.dlctt.mvvmlearning.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.ui.tasks.TasksActivity
import com.dlctt.mvvmlearning.utils.handleLoading
import com.dlctt.mvvmlearning.utils.handleMessages
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {
//    private val _tag = this.javaClass.simpleName

    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        handleLoading(loading_indicator, viewModel.isLoading())
        handleMessages(true, viewModel.getDialogMessage())
        observeInputErrors()
        observeNavigation()
    }

    private fun observeInputErrors() {
        viewModel.getInputError().observe(viewLifecycleOwner, Observer {
            it?.getContent()?.let { errMsg ->
                if (errMsg == "ok")
                    user_id_field.error = null
                else
                    user_id_field.error = errMsg
            }
        })
    }

    private fun observeNavigation() {
        viewModel.navigateToMain().observe(viewLifecycleOwner, Observer { event ->
            event?.getContent()?.let { navigate ->
                if (navigate)
                    navigateToTasksScreen()
            }
        })
    }

    private fun setupViews() {
        login_btn.setOnClickListener {
            viewModel.validateInput(user_id_field.text.toString())
        }
    }

    private fun navigateToTasksScreen() {
        startActivity(Intent(context, TasksActivity::class.java))
    }
}