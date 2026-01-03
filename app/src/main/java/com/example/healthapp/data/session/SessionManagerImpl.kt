package com.example.healthapp.data.session

import com.example.healthapp.data.remote.FirebaseAuthService
import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.domain.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class SessionManagerImpl  @Inject constructor(
    private val authService: FirebaseAuthService
) : SessionManager {

    private val _session = MutableStateFlow<UserSession>(UserSession.Guest)
    override val session: StateFlow<UserSession> = _session

    init {
        refreshSession()
    }

    private fun refreshSession() {
        val uid = authService.currentUserId()
        _session.value =
            if (uid != null) {
                UserSession.LoggedIn(uid)
            } else {
                UserSession.Guest
            }
    }

    override fun isLoggedIn(): Boolean =
        _session.value is UserSession.LoggedIn

    override fun requireUserId(): String =
        (_session.value as? UserSession.LoggedIn)?.userId
            ?: throw IllegalStateException("User must be logged in")

    override suspend fun logout() {
        // 1️⃣ Firebase sign out
        authService.logout()

        // 2️⃣ Update session to Guest
        _session.value = UserSession.Guest
    }
}
