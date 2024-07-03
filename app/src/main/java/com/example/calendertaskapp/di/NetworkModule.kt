package com.example.calendertaskapp.di

import com.example.calendertaskapp.repository.RetrofitInstance
import com.example.calendertaskapp.repository.TaskApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideTaskApiService(): TaskApiService {
        return RetrofitInstance.api // Assuming RetrofitInstance provides TaskApiService
    }
}