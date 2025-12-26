package com.example.healthapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.healthapp.data.local.entity.WeightRecordEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface WeightRecordDao {
    @Query("""
        SELECT * FROM weight_record
        WHERE userId = :userId
        ORDER BY measuredAt DESC
    """)
    fun observeWeights(userId: String): Flow<List<WeightRecordEntity>>

    @Query("""
        SELECT * FROM weight_record 
        WHERE userId = :userId 
        ORDER BY measuredAt DESC 
        LIMIT 1
    """)
    suspend fun getLatestWeight(userId: String): WeightRecordEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(record: WeightRecordEntity)

    @Delete
    suspend fun delete(record: WeightRecordEntity)
}