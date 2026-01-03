package com.example.healthapp.data.remote

import com.example.healthapp.domain.model.AuthUser
import com.example.healthapp.domain.repository.AuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import javax.inject.Inject


class FirebaseAuthService @Inject constructor(
    private val auth: FirebaseAuth
): AuthRepository {
    fun currentUserId(): String? = auth.currentUser?.uid

    suspend fun signInWithGoogle(idToken: String): AuthResult =
        auth.signInWithCredential(
            GoogleAuthProvider.getCredential(idToken, null)
        ).await()

    override fun getCurrentUser(): AuthUser? {
        val user = auth.currentUser ?: return null
        return AuthUser(
            userId = user.uid,
            displayName = user.displayName,
            photoUrl = user.photoUrl?.toString()
        )
    }

    fun logout() {
        auth.signOut()
    }
}
