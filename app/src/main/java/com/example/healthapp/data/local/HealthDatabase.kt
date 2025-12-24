package com.example.healthapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.local.entity.WaterIntakeEntity
import com.example.healthapp.data.local.entity.SleepSessionEntity


@Database(
    entities = [
        WaterIntakeEntity::class,
        SleepSessionEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDao
}