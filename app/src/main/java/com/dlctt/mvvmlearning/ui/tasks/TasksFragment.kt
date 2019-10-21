package com.dlctt.mvvmlearning.ui.tasks

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.Constants
import com.dlctt.mvvmlearning.utils.ListItemCallback
import com.dlctt.mvvmlearning.utils.handleUIState
import com.dlctt.mvvmlearning.utils.showToast
import kotlinx.android.synthetic.main.fragment_tasks.*

/**
 * Created by abdelhafiz on 9/25/19.
 */
class TasksFragment : Fragment(), ListItemCallback<Task> {

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter(this) }

    private val viewModel: TasksViewModel by lazy {
        ViewModelProviders.of(
            this, TasksViewModelFactory(
                arguments?.getInt(Constants.USER_ID) ?: 0
            )
        ).get(TasksViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()
    }

    private fun setupViews() {
        with(tasks_list) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = tasksAdapter
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getResourceLiveData().observe(viewLifecycleOwner, Observer { resource ->
            handleUIState(resource, loading_indicator, true)
            if (resource is com.dlctt.mvvmlearning.model.DTO.Resource.Result.Success) {
                tasksAdapter.submitList(resource.data)
            }
        })
    }

    override fun onItemClicked(item: Task) {
        showToast(item.toString())
    }

}