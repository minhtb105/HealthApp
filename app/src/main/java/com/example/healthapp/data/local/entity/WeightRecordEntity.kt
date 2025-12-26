package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "weight_record",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId")]
)
data class WeightRecordEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val userId: String,
    var weightKg: Double = 0.0,
    val measuredAt: Long
)
