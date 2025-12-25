package com.example.healthapp.di

import com.example.healthapp.data.repository.WaterRepository
import com.example.healthapp.data.repository.WaterRepositoryImpl
import com.example.healthapp.data.repository.SleepRepository
import com.example.healthapp.data.repository.SleepRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindWaterRepository(
        impl: WaterRepositoryImpl
    ): WaterRepository

    @Binds
    abstract fun bindSleepRepository(
        impl: SleepRepositoryImpl
    ): SleepRepository
}
