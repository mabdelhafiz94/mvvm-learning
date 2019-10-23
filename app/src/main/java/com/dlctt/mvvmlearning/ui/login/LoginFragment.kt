package com.dlctt.mvvmlearning.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.databinding.FragmentLoginBinding
import com.dlctt.mvvmlearning.ui.MainActivity
import com.dlctt.mvvmlearning.utils.observeMessages

class LoginFragment : Fragment() {
    private val viewModel: LoginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    private lateinit var viewBinding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_login, container, false)
        viewBinding.lifecycleOwner = viewLifecycleOwner
        viewBinding.viewModel = viewModel
        return viewBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
        observeViewModel()
    }

    private fun observeViewModel() {
        observeMessages(true, viewModel.getToastMessage())
        observeMessages(false, viewModel.getDialogMessage())
        observeInputErrors()
        observeNavigation()
    }

    private fun observeInputErrors() {
        viewModel.getInputError().observe(viewLifecycleOwner, Observer {
            it?.getContent()?.let { errMsg ->
                if (errMsg == "ok")
                    viewBinding.userIdField.error = null
                else
                    viewBinding.userIdField.error = errMsg
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
        viewBinding.loginBtn.setOnClickListener {
            viewModel.validateInput(viewBinding.userIdField.text.toString())
        }
    }

    private fun navigateToTasksScreen() {
        startActivity(Intent(context, MainActivity::class.java))
    }
}