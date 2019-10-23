package com.dlctt.mvvmlearning.ui.taskDetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.databinding.FragmentTaskDetailsBinding
import com.dlctt.mvvmlearning.utils.observeMessages

/**
 * Created by abdelhafiz on 10/23/19.
 */
class TaskDetailsFragment : Fragment() {
    private lateinit var viewBinding: FragmentTaskDetailsBinding
    private val viewModel: TaskDetailsViewModel by lazy {
        ViewModelProviders.of(this).get(TaskDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_task_details, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getTaskById()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeMessages(false, viewModel.getDialogMessage())
    }

}