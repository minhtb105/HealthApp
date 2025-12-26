package com.example.healthapp.fake

import com.example.healthapp.domain.session.SessionManager


class FakeSessionManager(
    private var userId: String = "test_user"
) : SessionManager {

    override val currentUserId: String
        get() = userId

    fun setUser(userId: String) {
        this.userId = userId
    }
}
