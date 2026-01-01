package com.example.healthapp.data.remote

import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
) {
    fun currentUserId(): String? = auth.currentUser?.uid

    suspend fun signInWithGoogle(idToken: String): AuthResult =
        auth.signInWithCredential(
            GoogleAuthProvider.getCredential(idToken, null)
        ).await()

    fun logout() {
        auth.signOut()
    }
}
