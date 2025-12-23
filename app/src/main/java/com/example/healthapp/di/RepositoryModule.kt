package com.example.healthapp.di

import com.example.healthapp.data.repository.HealthRepository
import com.example.healthapp.data.repository.HealthRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHealthRepository(
        impl: HealthRepositoryImpl
    ): HealthRepository
}
