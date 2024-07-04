package com.example.calendertaskapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.calendertaskapp.databinding.ItemTaskLayoutBinding
import com.example.calendertaskapp.model.TaskModel

class TaskListAdapter(private val onDeleteClickListener: OnDeleteClickListener) : RecyclerView.Adapter<TaskListAdapter.ViewHolder>() {

    private var taskModels: List<TaskModel> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(newData: List<TaskModel>) {
        taskModels = newData
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTaskLayoutBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onDeleteClickListener)
    }

    override fun getItemCount() = taskModels.size


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val taskModel = taskModels[position]
        holder.onBind(taskModel)
    }

    class ViewHolder(val binding: ItemTaskLayoutBinding, private val onDeleteClickListener: OnDeleteClickListener) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(taskModel: TaskModel) {
            // Bind data to your views here
            with(binding) {
                dateDetail.text = taskModel.task_detail.date
                titleDetail.text = taskModel.task_detail.title
                descDetail.text = taskModel.task_detail.description

                deleteTask.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        onDeleteClickListener.onDeleteClick(taskModel)
                    }
                }
            }

        }

    }

    interface OnDeleteClickListener {
        fun onDeleteClick(task: TaskModel)
    }
}