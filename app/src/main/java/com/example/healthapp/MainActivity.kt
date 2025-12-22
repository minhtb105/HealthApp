package com.example.healthapp

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Button
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.healthapp.ui.water.WaterViewModel
import androidx.room.Room
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.repository.HealthRepositoryImpl
import com.example.healthapp.ui.water.WaterViewModelFactory
import com.example.healthapp.ui.water.WaterUiState
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: WaterViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Views
        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        val emptyText = findViewById<TextView>(R.id.emptyText)
        val totalText = findViewById<TextView>(R.id.totalText)
        val addButton = findViewById<Button>(R.id.addButton)

        val db = Room.databaseBuilder(
            applicationContext,
            HealthDatabase::class.java,
            "health_database"
        ).build()

        val repository = HealthRepositoryImpl(db.waterIntakeDao())

        viewModel = ViewModelProvider(
            this,
            WaterViewModelFactory(repository)
        )[WaterViewModel::class.java]

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