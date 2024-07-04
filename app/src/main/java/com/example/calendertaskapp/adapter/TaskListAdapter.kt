package com.example.calendertaskapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendertaskapp.databinding.ItemTaskLayoutBinding
import com.example.calendertaskapp.model.TaskDetail
import com.example.calendertaskapp.model.TaskModel

class TaskListAdapter() : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private var taskDetails: List<TaskDetail> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<TaskDetail>) {
        taskDetails = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            ItemTaskLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun getItemCount() = taskDetails.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = taskDetails[position]
        holder.onBind(task)
    }

    class ViewHolder(val binding: ItemTaskLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(task: TaskDetail) {
            // Bind data to your views here
            with(binding) {
                idDetail.text = task.id
                dateDetail.text = task.date
                titleDetail.text = task.title
                descDetail.text = task.description
            }

        }

    }
}