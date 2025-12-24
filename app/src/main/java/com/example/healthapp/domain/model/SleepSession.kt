package com.example.healthapp.domain.model


enum class SleepType {
    NIGHT,
    NAP
}

data class SleepSession(
    val id: Long,
    val startTime: Long,
    val endTime: Long,
    val durationMinutes: Long,
    val type: SleepType // NIGHT / NAP
)
