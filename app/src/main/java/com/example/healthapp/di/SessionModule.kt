package com.example.healthapp.di

import com.example.healthapp.data.session.SessionManagerImpl
import com.example.healthapp.domain.session.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    @Singleton
    abstract fun bindSessionManager(
        impl: SessionManagerImpl
    ): SessionManager
}
