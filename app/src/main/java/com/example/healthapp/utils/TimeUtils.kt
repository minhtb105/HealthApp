package com.example.healthapp.utils

import java.util.Calendar
import java.util.concurrent.TimeUnit


class TimeUtils(private val timeProvider: TimeProvider) {
    /**
     * @return Pair(startOfToday, endOfToday) in milliseconds
     */
    fun todayRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance().apply {
            timeInMillis = timeProvider.now()
        }

        // Start of day
        calendar.set(Calendar.HOUR_OF_DAY, 0)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)
        val startOfDay = calendar.timeInMillis

        // End of day
        calendar.set(Calendar.HOUR_OF_DAY, 23)
        calendar.set(Calendar.MINUTE, 59)
        calendar.set(Calendar.SECOND, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endOfDay = calendar.timeInMillis

        return startOfDay to endOfDay
    }

    /**
     * @return timestamp of 7 days ago (from now)
     */
    fun last7DaysStart(): Long {
        return timeProvider.now() -
                TimeUnit.DAYS.toMillis(7)
    }
}