package com.dlctt.mvvmlearning.ui.tasks

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.DialogManager
import kotlinx.android.synthetic.main.activity_tasks.*

class TasksActivity : AppCompatActivity(), TasksContract.View {
    private val presenter: TasksContract.Presenter by lazy {
        TasksPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        presenter.loadTasks()
    }

    override fun onTasksLoaded(tasks: List<Task>) {
    }

    override fun showLoading(show: Boolean) {
        if (show)
            loading_indicator.visibility = View.VISIBLE
        else
            loading_indicator.visibility = View.GONE
    }

    override fun showDialogMessage(message: String) {
        DialogManager.showDialog(this, message)
    }

}
