package com.example.healthapp.data.session

import com.example.healthapp.domain.session.SessionManager
import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class SessionManagerImpl  @Inject constructor(
    private val auth: FirebaseAuth
) : SessionManager {

    override val currentUserId: String
        get() = auth.currentUser?.uid
            ?: throw IllegalStateException("User not logged in")
}
