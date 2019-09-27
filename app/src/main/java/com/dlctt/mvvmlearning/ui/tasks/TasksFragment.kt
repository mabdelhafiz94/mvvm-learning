package com.dlctt.mvvmlearning.ui.tasks

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.ListItemCallback
import com.dlctt.mvvmlearning.utils.handleErrorMsg
import com.dlctt.mvvmlearning.utils.handleLoading
import com.dlctt.mvvmlearning.utils.showToast
import kotlinx.android.synthetic.main.fragment_tasks.*

/**
 * Created by abdelhafiz on 9/25/19.
 */
class TasksFragment : Fragment(), ListItemCallback<Task> {

    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter(this) }

    private val viewModel: TasksViewModel =
        ViewModelProviders.of(this).get(TasksViewModel::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        bundle: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tasks, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupViews()

        with(viewModel) {
            loadTasks().observe(viewLifecycleOwner, Observer { list ->
                tasksAdapter.submitList(list ?: emptyList())
            })

            handleErrorMsg(getErrorMsgLiveData())
            handleLoading(isLoadingLiveData(), loading_indicator)
        }
    }

    private fun setupViews() {
        with(tasks_list) {
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
            addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
            adapter = tasksAdapter
        }
    }

    override fun onItemClicked(item: Task) {
        showToast(item.toString())
    }

}