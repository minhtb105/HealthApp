package com.example.healthapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.local.dao.SleepSessionDao
import com.example.healthapp.data.local.dao.UserProfileDao
import com.example.healthapp.data.local.dao.WeightRecordDao
import com.example.healthapp.data.local.entity.WaterIntakeEntity
import com.example.healthapp.data.local.entity.SleepSessionEntity
import com.example.healthapp.data.local.entity.WeightRecordEntity
import com.example.healthapp.data.local.entity.UserProfileEntity


@Database(
    entities = [
        WaterIntakeEntity::class,
        SleepSessionEntity::class,
        UserProfileEntity::class,
        WeightRecordEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class HealthDatabase : RoomDatabase() {
    abstract fun waterIntakeDao(): WaterIntakeDao
    abstract fun sleepSessionDao(): SleepSessionDao
    abstract fun userProfileDao(): UserProfileDao
    abstract fun weightRecordDao(): WeightRecordDao
}
