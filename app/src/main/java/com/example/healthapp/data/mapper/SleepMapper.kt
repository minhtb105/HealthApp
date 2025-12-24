package com.example.healthapp.data.mapper

import com.example.healthapp.data.local.entity.SleepRecordEntity
import com.example.healthapp.domain.model.SleepRecord
import java.time.LocalDate


fun SleepRecordEntity.toDomain(): SleepRecord =
    SleepRecord(
        id = id,
        sleepTime = sleepTime,
        wakeTime = wakeTime,
        durationHours = durationHours,
        date = LocalDate.parse(date)
    )

fun SleepRecord.toEntity(): SleepRecordEntity =
    SleepRecordEntity(
        id = id,
        sleepTime = sleepTime,
        wakeTime = wakeTime,
        durationHours = durationHours,
        date = date.toString()
    )
