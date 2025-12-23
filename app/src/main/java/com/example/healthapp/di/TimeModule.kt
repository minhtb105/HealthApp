package com.example.healthapp.di

import com.example.healthapp.utils.SystemTimeProvider
import com.example.healthapp.utils.TimeProvider
import com.example.healthapp.utils.TimeUtils
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object TimeModule {

    @Provides
    @Singleton
    fun provideTimeProvider(): TimeProvider =
        SystemTimeProvider()

    @Provides
    fun provideTimeUtils(
        timeProvider: TimeProvider
    ): TimeUtils = TimeUtils(timeProvider)
}