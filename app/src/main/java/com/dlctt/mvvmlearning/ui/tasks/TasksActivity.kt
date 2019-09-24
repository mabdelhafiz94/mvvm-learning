package com.dlctt.mvvmlearning.ui.tasks

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.hide
import com.dlctt.mvvmlearning.utils.show
import com.dlctt.mvvmlearning.utils.showDialog
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
            loading_indicator.show()
        else
            loading_indicator.hide()
    }

    override fun showDialogMessage(message: String) {
        showDialog(message)
    }

}
