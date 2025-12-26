package com.example.healthapp.data.mapper.weight

import com.example.healthapp.data.local.entity.WeightRecordEntity
import com.example.healthapp.domain.model.WeightRecord


fun WeightRecordEntity.toDomain(): WeightRecord = WeightRecord(
    id = id,
    weightKg = weightKg,
    measuredAt = measuredAt
)

fun WeightRecord.toEntity(userId: String): WeightRecordEntity = WeightRecordEntity(
    id = id,
    userId = userId,
    weightKg = weightKg,
    measuredAt = measuredAt
)
