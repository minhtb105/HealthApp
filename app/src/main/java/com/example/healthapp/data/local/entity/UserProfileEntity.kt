package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "user_profile")
data class UserProfileEntity (
    @PrimaryKey val userId: String,
    val heightCm: Int,
    val birthDate: String,
    val gender: String
)
