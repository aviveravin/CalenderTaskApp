package com.example.calendertaskapp.model

data class TaskRequest(
    val user_id: Int,
    val task: TaskDetails
)

data class TaskDetails(
    val title: String,
    val description: String
)

data class DataResponse(
    val taskId: Int,
    val taskDetail: TaskResponse
)

data class TaskResponse(
    val id: String,
    val date: String,
    val title: String,
    val description: String
)

