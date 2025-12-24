package com.example.healthapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.healthapp.data.local.entity.SleepRecordEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface SleepSessionDao {

    /**
     * Observe all sleep sessions (history screen)
     */
    @Query("""
        SELECT * FROM sleep_sessions
        ORDER BY startTime DESC
    """)
    fun observeAll(): Flow<List<SleepRecordEntity>>

    /**
     * Observe sleep sessions of a specific day
     * (night sleep + naps)
     */
    @Query("""
        SELECT * FROM sleep_sessions
        WHERE startDate = :date
        ORDER BY startTime ASC
    """)
    fun observeByDate(date: String): Flow<List<SleepRecordEntity>>

    /**
     * Get sleep sessions in a time range
     * (weekly / monthly stats)
     */
    @Query("""
        SELECT * FROM sleep_sessions
        WHERE startTime BETWEEN :from AND :to
        ORDER BY startTime ASC
    """)
    suspend fun getInRange(
        from: Long,
        to: Long
    ): List<SleepRecordEntity>

    /**
     * Insert or update a sleep session
     */
    @Upsert
    suspend fun upsert(session: SleepRecordEntity)

    /**
     * Delete a session
     */
    @Query("DELETE FROM sleep_sessions WHERE id = :id")
    suspend fun deleteById(id: Long)
}
