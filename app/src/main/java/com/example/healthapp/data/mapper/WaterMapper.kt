package com.example.healthapp.data.mapper.water

import com.example.healthapp.data.local.entity.WaterIntakeEntity
import com.example.healthapp.domain.model.WaterIntake


fun WaterIntakeEntity.toDomain(): WaterIntake {
    return WaterIntake(
        id = id,
        amountMl = amountMl,
        timestamp = timestamp
    )
}

fun WaterIntake.toEntity(userId: String): WaterIntakeEntity {
    return WaterIntakeEntity(
        id = id,
        userId = userId,
        amountMl = amountMl,
        timestamp = timestamp
    )
}
