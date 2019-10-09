package com.dlctt.mvvmlearning.ui.login

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Resource
import com.dlctt.mvvmlearning.ui.tasks.TasksActivity
import com.dlctt.mvvmlearning.utils.Constants
import com.dlctt.mvvmlearning.utils.handleUIState
import com.dlctt.mvvmlearning.utils.showDialog
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
        viewModel.getInputErrorLiveData().observe(viewLifecycleOwner, Observer { event ->
            event?.let {
                with(event.getContent())
                {
                    user_id_field.error = this
                    if (this == null)
                        observeLogin()
                }
            }
        })
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupViews()
    }

    private fun setupViews() {
        login_btn.setOnClickListener {
            viewModel.validateInput(user_id_field.text.toString())
        }
    }

    private fun observeLogin() {
        viewModel.login(user_id_field.text.toString())
            .observe(viewLifecycleOwner, Observer { event ->
                event?.getContent()?.let { resource ->
                    handleUIState(resource, loading_indicator, false)

                    if (resource is Resource.Success) {
                        showDialog(
                            "Login success with user email: ${resource.data!![0].email}",
                            DialogInterface.OnClickListener { _: DialogInterface, _: Int ->
                                navigateToTasksScreen(
                                    resource.data[0].id
                                )
                            })
                    }
                }
            })
    }

    private fun navigateToTasksScreen(userId: Int) {
        val toTasks = Intent(context, TasksActivity::class.java)
        toTasks.putExtra(Constants.USER_ID, userId)
        startActivity(toTasks)
    }
}