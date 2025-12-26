package com.example.healthapp.data.mapper.user

import com.example.healthapp.data.local.entity.UserProfileEntity
import com.example.healthapp.domain.model.Gender
import com.example.healthapp.domain.model.UserProfile


fun UserProfileEntity.toDomain(): UserProfile =
    UserProfile(
        userId = userId,
        heightCm = heightCm,
        birthDate = birthDate,
        gender = Gender.valueOf(gender)
    )

fun UserProfile.toEntity(): UserProfileEntity =
    UserProfileEntity(
        userId = userId,
        heightCm = heightCm,
        birthDate = birthDate,
        gender = gender.name
    )
