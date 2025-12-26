package com.example.healthapp.domain.repository

import com.example.healthapp.domain.model.WaterIntake

interface WaterRepository {

    suspend fun addWaterIntake(waterIntake: WaterIntake)

    suspend fun getTodayWaterIntake(): List<WaterIntake>

    suspend fun getWaterIntakeLast7Days(): List<WaterIntake>

    suspend fun getWaterIntakeInRange(
        from: Long,
        to: Long
    ): List<WaterIntake>
}

