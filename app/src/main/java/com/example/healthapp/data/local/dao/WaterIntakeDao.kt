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
}