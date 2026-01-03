package com.example.healthapp.domain.session

import kotlinx.coroutines.flow.StateFlow


interface SessionManager {
    val session: StateFlow<UserSession>

    fun isLoggedIn(): Boolean

    fun requireUserId(): String
    suspend fun logout()
}
