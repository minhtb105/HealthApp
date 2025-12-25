package com.example.healthapp.data.repository

import com.example.healthapp.domain.model.SleepSession
import kotlinx.coroutines.flow.Flow


interface SleepRepository {
    /**
     * Observe all sleep sessions (history screen)
     */
    fun observeAll(): Flow<List<SleepSession>>

    /**
     * Observe sleep sessions of a specific day
     * (night sleep + naps)
     */
    fun observeByDate(date: String): Flow<List<SleepSession>>

    /**
     * Get sleep sessions in a time range
     * (weekly / monthly stats)
     */
    suspend fun getSleepSessionsInRange(
        from: Long,
        to: Long
    ): List<SleepSession>

    /**
     * Insert or update a sleep session
     */
    suspend fun upsertSleepSession(session: SleepSession)

    /**
     * Delete a sleep session
     */
    suspend fun deleteSleepSession(id: Long)
}