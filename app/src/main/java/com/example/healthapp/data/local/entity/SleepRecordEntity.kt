package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "sleep_records")
data class SleepRecordEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val sleepTime: Long,
    val wakeTime: Long,
    val durationHours: Long,
    val date: String
)
