package com.example.healthapp.utils

import java.util.Calendar
import java.util.concurrent.TimeUnit


object TimeUtils {

    /**
     * @return Pair(startOfToday, endOfToday) in milliseconds
     */
    fun todayRange(): Pair<Long, Long> {
        val calendar = Calendar.getInstance()

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
        return System.currentTimeMillis() -
                TimeUnit.DAYS.toMillis(7)
    }
}