package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.databinding.FragmentTasksBinding
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.ui.taskDetails.TaskDetailsFragment
import com.dlctt.mvvmlearning.utils.ListItemCallback
import com.dlctt.mvvmlearning.utils.navigateToFragment
import com.dlctt.mvvmlearning.utils.observeMessages

/**
 * Created by abdelhafiz on 9/25/19.
 */
class TasksFragment : Fragment(), ListItemCallback<Task> {
    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter(this) }

    private val viewModel: TasksViewModel by lazy {
        ViewModelProviders.of(this).get(TasksViewModel::class.java)
    }
    private lateinit var viewBinding: FragmentTasksBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_tasks, container, false)
        viewBinding.viewModel = viewModel
        viewBinding.lifecycleOwner = viewLifecycleOwner
        return viewBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        with(viewBinding.tasksList) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = tasksAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        observeMessages(false, viewModel.getDialogMessage())
        observeTasks()
        observeNavigation()
    }

    private fun observeTasks() {
        viewModel.getTasksLiveData().observe(viewLifecycleOwner, Observer {
            it?.let { tasks ->
                tasksAdapter.submitList(tasks)
            }
        })
    }

    private fun observeNavigation() {
        viewModel.navigateToTaskDetails().observe(viewLifecycleOwner, Observer { event ->
            event?.getContent()?.let { navigate ->
                if (navigate)
                    navigateToDetailsScreen()
            }
        })
    }

    private fun navigateToDetailsScreen() {
        (activity as AppCompatActivity).navigateToFragment(
            TaskDetailsFragment(),
            false,
            R.id.fragments_main_container
        )
    }

    override fun onItemClicked(item: Task) {
        viewModel.onTaskSelected(item)
    }

}