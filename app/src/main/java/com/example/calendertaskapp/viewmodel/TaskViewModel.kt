package com.example.calendertaskapp.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.calendertaskapp.model.DeleteTaskRequest
import com.example.calendertaskapp.model.StoreTaskResponse
import com.example.calendertaskapp.model.TaskModel
import com.example.calendertaskapp.model.TaskRequest
import com.example.calendertaskapp.model.TaskResponseList
import com.example.calendertaskapp.repository.TaskRepository
import com.example.calendertaskapp.utils.ResponseResult
import com.example.calendertaskapp.utils.ViewState
import com.example.calendertaskapp.utils.shareWhileObserved
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskViewModel @Inject constructor(
    private val repository: TaskRepository
) : ViewModel(){

    private val _getAllTaskFlow = MutableSharedFlow<ViewState<TaskResponseList>>()
    val getAllTaskFlow: SharedFlow<ViewState<TaskResponseList>> =
        _getAllTaskFlow.shareWhileObserved(viewModelScope)

    private val _storeTaskFlow = MutableSharedFlow<ViewState<StoreTaskResponse>>()
    val storeTaskFlow: SharedFlow<ViewState<StoreTaskResponse>> =
        _storeTaskFlow.shareWhileObserved(viewModelScope)

    private val _deleteTaskFlow = MutableSharedFlow<ViewState<StoreTaskResponse>>()
    val deleteTaskFlow: SharedFlow<ViewState<StoreTaskResponse>> =
        _deleteTaskFlow.shareWhileObserved(viewModelScope)

    fun getTaskList(userId: Int) {
        viewModelScope.launch {
            Log.d("TaskViewModel", "Task impl= $")
            _getAllTaskFlow.emit(ViewState.loading())
            val viewState = when (val responseState =
                repository.getTasks(userId)) {
                is ResponseResult.Success -> ViewState.success(responseState.data)
                is ResponseResult.Error -> ViewState.failed(responseState.message)
                is ResponseResult.NetworkException -> ViewState.NetworkFailed(responseState.networkError)
                is ResponseResult.ErrorException -> ViewState.exceptionError(responseState.exception)
                else -> ViewState.exceptionError(responseState.toString())
            }
            _getAllTaskFlow.emit(viewState)
        }
    }

    fun addTask(taskRequest: TaskRequest) {
        viewModelScope.launch {
            Log.d("TaskViewModel", "Task impl= $")
            _storeTaskFlow.emit(ViewState.loading())
            val viewState = when (val responseState =
                repository.addTask(taskRequest)) {
                is ResponseResult.Success -> ViewState.success(responseState.data)
                is ResponseResult.Error -> ViewState.failed(responseState.message)
                is ResponseResult.NetworkException -> ViewState.NetworkFailed(responseState.networkError)
                is ResponseResult.ErrorException -> ViewState.exceptionError(responseState.exception)
                else -> ViewState.exceptionError(responseState.toString())
            }
            _storeTaskFlow.emit(viewState)
        }
    }

    fun deleteTask(userId: Int, taskId: Int) {
        viewModelScope.launch {
            Log.d("TaskViewModel", "Task impl= $")
            _deleteTaskFlow.emit(ViewState.loading())
            val viewState = when (val responseState =
                repository.deleteTask(userId,taskId)) {
                is ResponseResult.Success -> ViewState.success(responseState.data)
                is ResponseResult.Error -> ViewState.failed(responseState.message)
                is ResponseResult.NetworkException -> ViewState.NetworkFailed(responseState.networkError)
                is ResponseResult.ErrorException -> ViewState.exceptionError(responseState.exception)
                else -> ViewState.exceptionError(responseState.toString())
            }
            _deleteTaskFlow.emit(viewState)
        }
    }
}
