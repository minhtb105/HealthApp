package com.example.healthapp.domain.model


enum class Gender {
    MALE,
    FEMALE,
    OTHER
}

data class UserProfile (
    val userId: String,
    val heightCm: Int,
    val birthDate: String,
    val gender: Gender
)
