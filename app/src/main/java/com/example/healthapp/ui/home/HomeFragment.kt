package com.example.healthapp.ui.home


import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.healthapp.R
import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.ui.auth.AuthActivity
import com.example.healthapp.ui.water.WaterUiState
import com.example.healthapp.ui.water.WaterViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewModel: WaterViewModel by viewModels()
    @Inject
    lateinit var sessionManager: SessionManager

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val addButton = view.findViewById<Button>(R.id.addButton)
        val progressBar = view.findViewById<ProgressBar>(R.id.progressBar)
        val totalText = view.findViewById<TextView>(R.id.totalText)
        val emptyText = view.findViewById<TextView>(R.id.emptyText)

        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect { state ->
                // render as before
                progressBar.visibility = View.GONE
                emptyText.visibility = View.GONE
                totalText.visibility = View.GONE
                when (state) {
                    is WaterUiState.Loading -> progressBar.visibility = View.VISIBLE
                    is WaterUiState.Empty -> emptyText.visibility = View.VISIBLE
                    is WaterUiState.Success -> {
                        totalText.visibility = View.VISIBLE
                        totalText.text = getString(R.string.water_total, state.totalAmountMl)
                    }
                    is WaterUiState.Error -> {
                        emptyText.visibility = View.VISIBLE
                        emptyText.text = state.message
                    }
                }
            }
        }

        addButton.setOnClickListener {
            if (!sessionManager.isLoggedIn()) {
                // guest -> open AuthActivity to log in
                startActivity(Intent(requireContext(), AuthActivity::class.java))
            } else {
                viewModel.addWater(200)
            }
        }
    }
}
