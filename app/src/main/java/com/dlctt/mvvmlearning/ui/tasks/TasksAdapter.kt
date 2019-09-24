package com.dlctt.mvvmlearning.ui.tasks

import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.recyclerview.extensions.ListAdapter
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dlctt.mvvmlearning.R
import com.dlctt.mvvmlearning.model.DTO.Task
import com.dlctt.mvvmlearning.utils.ListItemCallback
import kotlinx.android.synthetic.main.task_item_layout.view.*


class TasksAdapter(private val itemCallback: ListItemCallback<Task>) :
    ListAdapter<Task, TasksAdapter.TaskViewHolder>(StoryDiffCallback()) {

    private val list: List<Task> by lazy { ArrayList<Task>() }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {

        return TaskViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.task_item_layout, parent,
                false
            )
        )
    }

    override fun onBindViewHolder(taskViewHolder: TaskViewHolder, position: Int) {
        taskViewHolder.bind(getItem(position))
    }

    override fun submitList(list: List<Task>?) {
        if (list == null) return

        with((this.list as ArrayList))
        {
            clear()
            addAll(list)
            super.submitList(ArrayList(this@TasksAdapter.list))
        }
    }

    fun addNewItemsToList(list: List<Task>) {
        (this.list as ArrayList).addAll(list)
        submitList(ArrayList(this.list))
    }

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView),
        View.OnClickListener {
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

            if (v == null || adapterPosition == RecyclerView.NO_POSITION) return
            itemCallback.onItemClicked(getItem(adapterPosition))
        }

        fun bind(item: Task) {
            with(itemView) {
                task_title.text = item.title
                task_completed_indicator.background = if (item.completed) {
                    ColorDrawable(ContextCompat.getColor(context, R.color.colorPrimary))
                } else {
                    ColorDrawable(ContextCompat.getColor(context, R.color.colorAccent))
                }
            }
        }
    }

    private class StoryDiffCallback : DiffUtil.ItemCallback<Task>() {
        override fun areItemsTheSame(p0: Task, p1: Task): Boolean {
            return p0.id == p1.id
        }

        override fun areContentsTheSame(p0: Task, p1: Task): Boolean {
            return p0.id == p1.id
        }
    }

}