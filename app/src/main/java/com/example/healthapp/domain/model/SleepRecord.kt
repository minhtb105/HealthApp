package com.example.healthapp.domain.model

import java.time.LocalDate


data class SleepRecord(
    val id: Long,
    val sleepTime: Long,
    val wakeTime: Long,
    val durationHours: Long,
    val date: LocalDate
)
