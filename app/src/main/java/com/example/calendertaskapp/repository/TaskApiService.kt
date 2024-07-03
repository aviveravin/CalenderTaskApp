package com.example.calendertaskapp.repository

import com.example.calendertaskapp.model.TaskModel
import com.example.calendertaskapp.model.TaskRequest
import com.example.calendertaskapp.model.TaskResponseList
import com.example.calendertaskapp.model.UserIdRequest
import com.example.calendertaskapp.utils.ResponseResult
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.POST
import retrofit2.http.Path

interface TaskApiService {

    @POST("api/getCalendarTaskList")
    suspend fun getTasks(
        @Body userId: UserIdRequest
    ): TaskResponseList

    @POST("api/storeCalendarTask")
    suspend fun addTask(
        @Body task: TaskRequest
    ): TaskRequest

    @DELETE("api/deleteCalendarTask/{id}")
    suspend fun deleteTask(
        @Path("id") id: Int
    )
}