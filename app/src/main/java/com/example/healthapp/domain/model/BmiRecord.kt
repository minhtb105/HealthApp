package com.example.healthapp.domain.model


enum class BmiCategory {
    UNDERWEIGHT,
    NORMAL,
    OVERWEIGHT,
    OBESE
}

data class BmiRecord (
    val bmi: Double,
    val category: BmiCategory,
    val measuredAt: Long
)
