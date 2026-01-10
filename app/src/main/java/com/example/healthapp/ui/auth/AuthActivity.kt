package com.example.healthapp.ui.auth

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.healthapp.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class AuthActivity : ComponentActivity() {
    private val viewModel: AuthViewModel by viewModels()
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    account.idToken?.let {
                        viewModel.firebaseAuthWithGoogle(it)
                    }
                } catch (e: ApiException) {
                    e.printStackTrace()
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupGoogleSignIn()

        setContent {
            val uiState = viewModel.uiState.collectAsState().value

            AuthScreen(
                uiState = uiState,
                onGoogleSignInClick = {
                    googleSignInClient.signOut()
                    googleSignInLauncher.launch(googleSignInClient.signInIntent)
                },
                onLoginSuccess = { finish() }
            )
        }
    }

    private fun setupGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    @Composable
    fun AuthScreen (
        uiState: AuthUiState,
        onGoogleSignInClick: () -> Unit,
        onLoginSuccess: () -> Unit
    ) {
        LaunchedEffect(uiState) {
            if (
                uiState is AuthUiState.LoggedInExistingUser ||
                uiState is AuthUiState.LoggedInNewUser
            ) {
                onLoginSuccess()
            }
        }

        when (uiState) {
            AuthUiState.Loading -> {
                Box (
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            AuthUiState.LoggedInExistingUser -> {
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            AuthUiState.LoggedInNewUser -> {
                // navigate to onboarding
                LaunchedEffect(Unit) {
                    onLoginSuccess()
                }
            }

            is AuthUiState.Error -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = uiState.message,
                        color = MaterialTheme.colorScheme.error
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    GoogleSignInButton(onClick = onGoogleSignInClick)
                }
            }

            AuthUiState.Idle -> {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    GoogleSignInButton(onClick = onGoogleSignInClick)
                }
            }
        }
    }

    @Composable
    fun GoogleSignInButton(
        onClick: () -> Unit
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_google),
                contentDescription = null,
                tint = androidx.compose.ui.graphics.Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text("Sign in with Google")
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun AuthScreenIdlePreview() {
        AuthScreen(
            uiState = AuthUiState.Idle,
            onGoogleSignInClick = {},
            onLoginSuccess = {}
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun AuthScreenLoadingPreview() {
        AuthScreen(
            uiState = AuthUiState.Loading,
            onGoogleSignInClick = {},
            onLoginSuccess = {}
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun AuthScreenErrorPreview() {
        AuthScreen(
            uiState = AuthUiState.Error("Login failed"),
            onGoogleSignInClick = {},
            onLoginSuccess = {}
        )
    }
}
