package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.WeightRecordDao
import com.example.healthapp.domain.model.WeightRecord
import com.example.healthapp.data.mapper.weight.toDomain
import com.example.healthapp.domain.repository.WeightRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.flow.map



class WeightRepositoryImpl @Inject constructor(
    private val dao: WeightRecordDao,
) : WeightRepository {
    override suspend fun getLatestWeight(userId: String): WeightRecord? =
        dao.getLatestWeight(userId)?.toDomain()

    override fun observeWeights(userId: String): Flow<List<WeightRecord>> =
        dao.observeWeights(userId)
            .map { list -> list.map { it.toDomain() } }
}