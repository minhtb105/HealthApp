package com.example.healthapp.domain.repository

import com.example.healthapp.domain.model.AuthUser


interface AuthRepository {
    fun getCurrentUser(): AuthUser?
}
