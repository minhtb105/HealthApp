package com.example.healthapp.utils


object HealthUtils {
    fun computeBmi(weightKg: Double, heightCm: Int): Double {
        if (heightCm <= 0) throw IllegalArgumentException("height must be > 0")
        val heightM = heightCm / 100.0
        return weightKg / (heightM * heightM)
    }
}
