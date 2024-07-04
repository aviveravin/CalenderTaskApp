package com.example.calendertaskapp.model

data class TaskRequest(
    val user_id: Int,
    val task: TaskDetails
)

data class TaskDetails(
    val title: String,
    val description: String
)

data class TaskResponseList(
    val tasks: List<TaskModel>
)

data class TaskModel(
    val task_id: Int,
    val task_detail: TaskDetail
)

data class TaskDetail(
    val id: String?,
    val date: String?,
    val title: String?,
    val description: String?
)
data class UserIdRequest(
    val user_id: Int
)

