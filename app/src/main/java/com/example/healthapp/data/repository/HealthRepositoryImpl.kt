package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.mapper.toDomain
import com.example.healthapp.data.mapper.toEntity
import com.example.healthapp.domain.model.WaterIntake
import com.example.healthapp.utils.TimeUtils


class HealthRepositoryImpl(
    private val dao: WaterIntakeDao
) : HealthRepository {

    override suspend fun addWaterIntake(waterIntake: WaterIntake) {
        dao.insertWaterIntake(waterIntake.toEntity())
    }

    override suspend fun getTodayWaterIntake(): List<WaterIntake> {
        val (start, end) = TimeUtils.todayRange()
        return dao
            .getWaterIntakeForDay(start, end)
            .map { it.toDomain() }
    }

    override suspend fun getWaterIntakeLast7Days(): List<WaterIntake> {
        val startTime = TimeUtils.last7DaysStart()
        return dao
            .getWaterIntakeSince(startTime)
            .map { it.toDomain() }
    }

    override suspend fun getWaterIntakeInRange(
        from: Long,
        to: Long
    ): List<WaterIntake> {
        return dao
            .getWaterIntakeInRange(from, to)
            .map { it.toDomain() }
    }
}
