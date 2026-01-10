package com.example.healthapp.ui.auth

import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.compose.collectAsStateWithLifecycle


@Composable
fun AuthRoute(
    viewModel: AuthViewModel,
    onGoogleSignInClick: () -> Unit,
    onLoginSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState) {
        if (
            uiState is AuthUiState.LoggedInExistingUser ||
            uiState is AuthUiState.LoggedInNewUser
        ) {
            onLoginSuccess()
        }
    }

    AuthScreen(
        uiState = uiState,
        onGoogleSignInClick = onGoogleSignInClick
    )
}
