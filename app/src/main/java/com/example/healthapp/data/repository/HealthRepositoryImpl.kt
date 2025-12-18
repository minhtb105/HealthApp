package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.mapper.toDomain
import com.example.healthapp.data.mapper.toEntity
import com.example.healthapp.domain.model.WaterIntake


class HealthRepositoryImpl(
    private val dao: WaterIntakeDao
) : HealthRepository {

    override suspend fun addWaterIntake(waterIntake: WaterIntake) {
        dao.insertWaterIntake(waterIntake.toEntity())
    }

    override suspend fun getAllWaterIntake(): List<WaterIntake> {
        return dao.getAllWaterIntake().map { it.toDomain() }
    }
}
