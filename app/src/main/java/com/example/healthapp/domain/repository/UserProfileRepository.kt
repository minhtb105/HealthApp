package com.example.healthapp.domain.repository

import com.example.healthapp.domain.model.UserProfile


interface UserProfileRepository {
    suspend fun getUserProfile(userId: String): UserProfile
}
