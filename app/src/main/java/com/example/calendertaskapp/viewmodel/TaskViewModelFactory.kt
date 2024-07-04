package com.example.calendertaskapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.calendertaskapp.repository.TaskRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class TaskViewModelFactory @Inject constructor(private val instance: TaskRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(TaskRepository::class.java).newInstance(instance)
    }
}