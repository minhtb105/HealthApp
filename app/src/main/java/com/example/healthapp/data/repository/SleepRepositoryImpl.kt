package com.example.healthapp.data.repository

import com.example.healthapp.data.local.dao.SleepSessionDao
import com.example.healthapp.data.mapper.sleep.toDomain
import com.example.healthapp.data.mapper.sleep.toEntity
import com.example.healthapp.domain.model.SleepSession
import com.example.healthapp.domain.session.SessionManager
import com.example.healthapp.domain.repository.SleepRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class SleepRepositoryImpl @Inject constructor(
    private val dao: SleepSessionDao,
    private val sessionManager: SessionManager
) : SleepRepository {

    override fun observeAll(): Flow<List<SleepSession>> =
        dao.observeAll()
            .map { entities ->
                entities.map { it.toDomain() }
            }

    override fun observeByDate(date: String): Flow<List<SleepSession>> =
        dao.observeByDate(date)
            .map { entities ->
                entities.map { it.toDomain() }
            }

    override suspend fun getSleepSessionsInRange(
        from: Long,
        to: Long
    ): List<SleepSession> =
        dao.getInRange(from, to)
            .map { it.toDomain() }

    override suspend fun upsertSleepSession(session: SleepSession) {
        val userId = sessionManager.requireUserId()

        dao.upsert(session.toEntity(userId = userId))
    }

    override suspend fun deleteSleepSession(id: Long) {
        dao.deleteById(id)
    }
}
