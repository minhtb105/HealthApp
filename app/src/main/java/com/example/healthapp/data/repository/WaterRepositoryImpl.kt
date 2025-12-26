package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.mapper.water.toDomain
import com.example.healthapp.data.mapper.water.toEntity
import com.example.healthapp.domain.model.WaterIntake
import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.utils.TimeUtils
import javax.inject.Inject


class WaterRepositoryImpl @Inject constructor(
    private val dao: WaterIntakeDao,
    private val timeUtils: TimeUtils,
    private val sessionManager: SessionManager
) : WaterRepository {

    override suspend fun addWaterIntake(waterIntake: WaterIntake) {
        dao.insertWaterIntake(waterIntake.toEntity(sessionManager.currentUserId))
    }

    override suspend fun getTodayWaterIntake(): List<WaterIntake> {
        val (start, end) = timeUtils.todayRange()
        return dao
            .getWaterIntakeForDay(start, end)
            .map { it.toDomain() }
    }

    override suspend fun getWaterIntakeLast7Days(): List<WaterIntake> {
        val startTime = timeUtils.last7DaysStart()
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
