package com.example.calendertaskapp.repository

import android.util.Log
import com.example.calendertaskapp.model.DeleteTaskRequest
import com.example.calendertaskapp.model.StoreTaskResponse
import com.example.calendertaskapp.model.TaskModel
import com.example.calendertaskapp.model.TaskRequest
import com.example.calendertaskapp.model.TaskResponseList
import com.example.calendertaskapp.model.UserIdRequest
import com.example.calendertaskapp.utils.ResponseResult
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TaskRepository @Inject constructor(
    private val taskApiService: TaskApiService
) : TaskRepositoryImpl{

    private val TAG = TaskRepository::class.java.simpleName

    override suspend fun getTasks(userId: Int): ResponseResult<TaskResponseList> {
        val userIdRequest = UserIdRequest(userId)
        return try {
            val response = taskApiService.getTasks(userIdRequest)
            Log.d("TAG", "getAllTasks: check the response $response")
            ResponseResult.success(response)
        } catch (e: HttpException) {
            ResponseResult.networkError("Error")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "getTasks: Exception e = $e"
            )
            ResponseResult.exception("Error")
        }
    }

    override suspend fun addTask(taskRequest: TaskRequest): ResponseResult<StoreTaskResponse> {
        return try {
            val response = taskApiService.addTask(taskRequest)
            Log.d("TAG", "addTasks: check the response $response")
            ResponseResult.success(response)
        } catch (e: HttpException) {
            ResponseResult.networkError("Error")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "getTasks: Exception e = $e"
            )
            ResponseResult.exception("Error")
        }
    }

    override suspend fun deleteTask(userId: Int, taskId: Int): ResponseResult<StoreTaskResponse> {
        val deleteTaskRequest = DeleteTaskRequest(userId, taskId)
        return try {
            val response = taskApiService.deleteTask(deleteTaskRequest)
            Log.d("TAG", "deleteTask: task with id deleted successfully")
            ResponseResult.success(response)
        } catch (e: HttpException) {
            ResponseResult.networkError("Error")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "deleteTask: Exception e = $e"
            )
            ResponseResult.exception("Error")
        }
    }
}