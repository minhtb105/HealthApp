package com.example.healthapp.di

import com.example.healthapp.data.session.SessionManagerImpl
import com.example.healthapp.domain.session.SessionManager
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class SessionModule {

    @Binds
    abstract fun bindSessionManager(
        impl: SessionManagerImpl
    ): SessionManager
}
