package com.example.healthapp.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.healthapp.data.local.entity.WaterIntakeEntity


@Dao
interface WaterIntakeDao {
    @Insert
    suspend fun insertWaterIntake(entity: WaterIntakeEntity)

    @Query("SELECT * FROM water_intake ORDER BY timestamp DESC")
    suspend fun getAllWaterIntake(): List<WaterIntakeEntity>

    @Query("""
        SELECT * FROM water_intake
        WHERE timestamp BETWEEN :startOfDay AND :endOfDay
        ORDER BY timestamp DESC
    """)
    suspend fun getWaterIntakeForDay(
        startOfDay: Long,
        endOfDay: Long
    ): List<WaterIntakeEntity>

    @Query("""
        SELECT * FROM water_intake
        WHERE timestamp >= :startTime
        ORDER BY timestamp DESC
    """)
    suspend fun getWaterIntakeSince(
        startTime: Long
    ): List<WaterIntakeEntity>

    @Query("""
        SELECT * FROM water_intake
        WHERE timestamp BETWEEN :from AND :to
        ORDER BY timestamp DESC
    """)
    suspend fun getWaterIntakeInRange(
        from: Long,
        to: Long
    ): List<WaterIntakeEntity>
}