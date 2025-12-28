package com.example.healthapp.di


import com.example.healthapp.data.remote.FirebaseAuthService
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth =
        FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseAuthService(
        auth: FirebaseAuth
    ): FirebaseAuthService =
        FirebaseAuthService(auth)
}
