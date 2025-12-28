package com.example.healthapp.data.session

import com.example.healthapp.data.remote.FirebaseAuthService
import com.example.healthapp.domain.session.SessionManager
import javax.inject.Inject


class SessionManagerImpl  @Inject constructor(
    private val authService: FirebaseAuthService
) : SessionManager {

    override val currentUserId: String?
        get() = authService.currentUserId()
            ?: throw IllegalStateException("User not logged in")
}
