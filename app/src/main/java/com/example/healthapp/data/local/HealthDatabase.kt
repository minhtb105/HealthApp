package com.example.healthapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.local.entity.WaterIntakeEntity


@Database(
    entities = [WaterIntakeEntity::class],
    version = 1
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDao
}