package com.example.healthapp.di

import com.example.healthapp.data.repository.WaterRepository
import com.example.healthapp.data.repository.WaterRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindHealthRepository(
        impl: WaterRepositoryImpl
    ): WaterRepository
}
