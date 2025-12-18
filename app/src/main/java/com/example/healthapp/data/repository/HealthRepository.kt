package com.example.healthapp.data.repository

import com.example.healthapp.domain.model.WaterIntake


interface HealthRepository {

    suspend fun addWaterIntake(waterIntake: WaterIntake)

    suspend fun getAllWaterIntake(): List<WaterIntake>
}
