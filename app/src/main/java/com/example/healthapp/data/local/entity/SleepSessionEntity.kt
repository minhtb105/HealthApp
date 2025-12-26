package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "sleep_sessions",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("userId"),
        Index("startTime"),
        Index("startDate")
    ]
)
data class SleepSessionEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val startTime: Long,
    val endTime: Long,
    val durationMinutes: Long,
    val type: String,        // NIGHT / NAP
    val startDate: String    // yyyy-MM-dd (start time date)
)
