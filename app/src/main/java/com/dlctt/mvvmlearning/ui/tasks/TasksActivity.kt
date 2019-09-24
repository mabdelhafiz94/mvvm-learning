package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.*
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksContract.View, ListItemCallback<Task> {
    private val presenter: TasksContract.Presenter by lazy {
        TasksPresenter(this)
    }
    private val tasksAdapter: TasksAdapter by lazy { TasksAdapter(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)
        setupViews()
        presenter.loadTasks()
    }

    private fun setupViews() {
        tasks_list.layoutManager = LinearLayoutManager(this)
        tasks_list.itemAnimator = DefaultItemAnimator()
        tasks_list.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        tasks_list.adapter = tasksAdapter
    }

    override fun onTasksLoaded(tasks: List<Task>) {
        tasksAdapter.submitList(tasks)
    }

    override fun showLoading(show: Boolean) {
        if (show)
            loading_indicator.show()
        else
            loading_indicator.hide()
    }

    override fun showDialogMessage(message: String) {
        showDialog(message)
    }

    override fun onItemClicked(item: Task) {
        showToast(item.toString())
    }

}
