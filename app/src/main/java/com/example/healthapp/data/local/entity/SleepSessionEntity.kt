package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "sleep_sessions",
    indices = [
        Index("startDate"),
        Index("startTime")
    ]
)
data class SleepSessionEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val startTime: Long,
    val endTime: Long,
    val durationMinutes: Long,
    val type: String,        // NIGHT / NAP
    val startDate: String    // yyyy-MM-dd (start time date)
)
