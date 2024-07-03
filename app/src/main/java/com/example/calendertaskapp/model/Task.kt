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
    val task_response : List<TaskModel>
)
data class TaskModel(
    val task_id: Int,
    val taskDetail: List<TaskResponse>
)

data class TaskResponse(
    val id: String,
    val date: String,
    val title: String,
    val description: String
)
data class UserIdRequest(
    val user_id: Int
)

