package com.example.healthapp.domain.model

import java.time.LocalDate


data class SleepDaySummary(
    val date: LocalDate,
    val totalSleepMinutes: Long,
    val nightSleepMinutes: Long,
    val napMinutes: Long,
    val sessions: List<SleepSession>
)
