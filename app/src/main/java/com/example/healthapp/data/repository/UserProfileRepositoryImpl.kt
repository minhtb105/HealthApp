package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.UserProfileDao
import com.example.healthapp.data.mapper.user.toDomain
import com.example.healthapp.domain.model.UserProfile
import com.example.healthapp.domain.repository.UserProfileRepository
import javax.inject.Inject


class UserProfileRepositoryImpl @Inject constructor(
    private val userProfileDao: UserProfileDao,
) : UserProfileRepository {
    override suspend fun getUserProfile(userId: String): UserProfile? =
        userProfileDao.getUserProfile(userId)?.toDomain()

    override suspend fun upsertUserProfile(profile: UserProfile) {
        TODO("Not yet implemented")
    }
}
