package com.example.healthapp.data.mapper

import com.example.healthapp.data.local.entity.SleepRecordEntity
import com.example.healthapp.domain.model.SleepSession
import com.example.healthapp.domain.model.SleepType
import java.time.Instant
import java.time.ZoneId


fun SleepRecordEntity.toDomain(): SleepSession =
    SleepSession(
        id = id,
        startTime = startTime,
        endTime = endTime,
        durationMinutes = durationMinutes,
        type = SleepType.valueOf(type)
    )

/**
 * Domain -> Entity
 *
 * startDate derived from startTime
 */
fun SleepSession.toEntity(): SleepRecordEntity =
    SleepRecordEntity(
        id = id,
        startTime = startTime,
        endTime = endTime,
        durationMinutes = durationMinutes,
        type = type.name,
        startDate = Instant
            .ofEpochMilli(startTime)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toString()
    )
