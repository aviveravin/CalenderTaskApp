package com.example.calendertaskapp.model

import java.util.UUID

data class TaskRequest(
    val user_id: Int,
    val task: TaskData
)

data class TaskData(
    val title: String,
    val description: String,
    val date: String
)

data class StoreTaskResponse(
    val status : String
)

data class TaskResponseList(
    val tasks: List<TaskModel>
)

data class TaskModel(
    val task_id: Int,
    val task_detail: TaskDetail
)

data class TaskDetail(
    val date: String?,
    val title: String?,
    val description: String?
)
data class UserIdRequest(
    val user_id: Int
)

data class DeleteTaskRequest(
    val user_id: Int,
    val task_id: Int
)

