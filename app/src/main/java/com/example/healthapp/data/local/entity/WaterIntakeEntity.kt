package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "water_intake")
data class WaterIntakeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,

    val amountMl: Int,

    val timestamp: Long
)
