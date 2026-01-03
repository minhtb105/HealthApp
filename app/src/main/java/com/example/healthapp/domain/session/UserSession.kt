package com.example.healthapp.domain.session


sealed class UserSession {

    object Guest : UserSession()

    data class LoggedIn(
        val userId: String
    ) : UserSession()
}
