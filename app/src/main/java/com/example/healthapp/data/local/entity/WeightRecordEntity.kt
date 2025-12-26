package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "weight_record")
data class WeightRecordEntity (
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var weightKg: Double = 0.0,
    val measuredAt: Long
)
