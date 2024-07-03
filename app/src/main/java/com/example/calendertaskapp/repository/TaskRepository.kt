package com.example.calendertaskapp.repository

import android.util.Log
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
            ResponseResult.networkError("Constants.ERROR_MESSAGE")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "getTasks: Exception e = $e"
            )
            ResponseResult.exception("Constants.ERROR_MESSAGE")
        }
    }

    override suspend fun addTask(taskRequest: TaskRequest): ResponseResult<TaskRequest> {
        return try {
            val response = taskApiService.addTask(taskRequest)
            Log.d("TAG", "getAllTasks: check the response $response")
            ResponseResult.success(response)
        } catch (e: HttpException) {
            ResponseResult.networkError("Constants.ERROR_MESSAGE")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "getTasks: Exception e = $e"
            )
            ResponseResult.exception("Constants.ERROR_MESSAGE")
        }
    }

    override suspend fun deleteTask(id: Int): ResponseResult<Unit> {
        return try {
            taskApiService.deleteTask(id)
            Log.d("TAG", "deleteTask: task with id $id deleted successfully")
            ResponseResult.success(Unit)
        } catch (e: HttpException) {
            ResponseResult.networkError("Constants.ERROR_MESSAGE")
        } catch (connection: java.net.ConnectException) {
            ResponseResult.networkError(connection.toString())
        } catch (e: Exception) {
            Log.d(
                "TaskRepository",
                "deleteTask: Exception e = $e"
            )
            ResponseResult.exception("Constants.ERROR_MESSAGE")
        }
    }
}