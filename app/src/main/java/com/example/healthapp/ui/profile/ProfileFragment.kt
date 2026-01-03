package com.example.healthapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.healthapp.R
import com.example.healthapp.domain.repository.AuthRepository
import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.domain.session.UserSession
import com.example.healthapp.ui.auth.AuthActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject lateinit var authRepository: AuthRepository


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)
        val tvUser = view.findViewById<TextView>(R.id.tvUser)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                sessionManager.session.collect { session ->
                    when (session) {
                        is UserSession.Guest -> {
                            btnLogin.visibility = View.VISIBLE
                            btnLogout.visibility = View.GONE
                            tvUser.text = getString(R.string.guest)
                        }

                        is UserSession.LoggedIn -> {
                            btnLogin.visibility = View.GONE
                            btnLogout.visibility = View.VISIBLE

                            // load profile
                            val authUser = authRepository.getCurrentUser()
                            tvUser.text = authUser?.displayName ?: "User"
                        }
                    }
                }
            }
        }

        btnLogin.setOnClickListener {
            startActivity(Intent(requireContext(), AuthActivity::class.java))
        }

        btnLogout.setOnClickListener {
            lifecycleScope.launch {
                sessionManager.logout()
            }
        }
    }
}
