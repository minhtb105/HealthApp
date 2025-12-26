package com.example.healthapp.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(
    tableName = "water_intake",
    foreignKeys = [
        ForeignKey(
            entity = UserProfileEntity::class,
            parentColumns = ["userId"],
            childColumns = ["userId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("userId"), Index("timestamp")]
)
data class WaterIntakeEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val userId: String,
    val amountMl: Int,
    val timestamp: Long
)
