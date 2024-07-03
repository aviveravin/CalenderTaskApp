package com.example.calendertaskapp.repository

import com.example.calendertaskapp.model.TaskModel
import com.example.calendertaskapp.model.TaskRequest
import com.example.calendertaskapp.model.TaskResponseList
import com.example.calendertaskapp.utils.ResponseResult
import javax.inject.Singleton

@Singleton
interface TaskRepositoryImpl {

    suspend fun getTasks(userId: Int) : ResponseResult<TaskResponseList>
    suspend fun addTask(taskRequest: TaskRequest): ResponseResult<TaskRequest>
    suspend fun deleteTask(id: Int): ResponseResult<Unit>
}