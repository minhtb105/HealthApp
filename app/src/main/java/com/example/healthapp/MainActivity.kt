package com.example.healthapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import androidx.activity.viewModels
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.example.healthapp.ui.water.WaterUiState
import com.example.healthapp.ui.water.WaterViewModel


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: WaterViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Views
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val emptyText = findViewById<TextView>(R.id.emptyText)
        val totalText = findViewById<TextView>(R.id.totalText)
        val addButton = findViewById<Button>(R.id.addButton)

        lifecycleScope.launch {
            viewModel.uiState.collect { state ->
                renderState(
                    state,
                    progressBar,
                    emptyText,
                    totalText
                )
            }
        }

        // Button click
        addButton.setOnClickListener {
            viewModel.addWater(200)
        }
    }

    private fun renderState(
        state: WaterUiState,
        progressBar: ProgressBar,
        emptyText: TextView,
        totalText: TextView
    ) {
        progressBar.visibility = View.GONE
        emptyText.visibility = View.GONE
        totalText.visibility = View.GONE

        when (state) {
            is WaterUiState.Loading -> {
                progressBar.visibility = View.VISIBLE
            }

            is WaterUiState.Empty -> {
                emptyText.visibility = View.VISIBLE
            }

            is WaterUiState.Success -> {
                totalText.visibility = View.VISIBLE
                totalText.text = getString(
                    R.string.water_total,
                    state.totalAmountMl
                )
            }

            is WaterUiState.Error -> {
                emptyText.visibility = View.VISIBLE
                emptyText.text = getString(
                    R.string.water_error,
                    state.message
                )
            }
        }
    }
}
