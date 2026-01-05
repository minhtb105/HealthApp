package com.example.healthapp.fake

import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.domain.session.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow


class FakeSessionManager(
    private var initialUserId: String? = "test_user"
) : SessionManager {

    private val _session = MutableStateFlow<UserSession>(
        if (initialUserId != null) UserSession.LoggedIn(initialUserId!!) else UserSession.Guest
    )

    override val session: StateFlow<UserSession> = _session

    override fun isLoggedIn(): Boolean {
        return _session.value is UserSession.LoggedIn
    }

    override fun requireUserId(): String {
        return (_session.value as UserSession.LoggedIn).userId
    }

    override suspend fun logout() {
        _session.value = UserSession.Guest
    }

    fun logoutUser() {
        _session.value = UserSession.Guest
    }
}
