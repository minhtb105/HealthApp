package com.example.healthapp.data.remote

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject


class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun currentUserId(): String? = auth.currentUser?.uid

    fun logout() {
        auth.signOut()
    }
}
