package com.example.healthapp.domain.repository

import com.example.healthapp.domain.model.WeightRecord
import kotlinx.coroutines.flow.Flow


interface WeightRepository {
    suspend fun getLatestWeight(userId: String): WeightRecord?

    fun observeWeights(userId: String): Flow<List<WeightRecord>>
}
