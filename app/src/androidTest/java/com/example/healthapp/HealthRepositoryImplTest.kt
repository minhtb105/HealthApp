package com.example.healthapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.repository.HealthRepository
import com.example.healthapp.data.repository.HealthRepositoryImpl
import com.example.healthapp.domain.model.WaterIntake
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class HealthRepositoryImplTest {

    private lateinit var db: HealthDatabase
    private lateinit var repository: HealthRepository

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        ).build()

        repository = HealthRepositoryImpl(
            dao = db.waterIntakeDao()
        )
    }

    @Test
    fun addAndGetTodayWaterIntake_returnsOnlyTodayData() = runBlocking {
        val now = System.currentTimeMillis()

        // insert today's data
        repository.addWaterIntake(
            WaterIntake(
                id = 0,
                amountMl = 300,
                timestamp = now
            )
        )

        val todayList = repository.getTodayWaterIntake()

        assertEquals(1, todayList.size)
        assertEquals(300, todayList.first().amountMl)
    }

    @Test
    fun getWaterIntakeInRange_returnsCorrectData() = runBlocking {
        val start = System.currentTimeMillis() - 60_000
        val end = System.currentTimeMillis() + 60_000

        repository.addWaterIntake(
            WaterIntake(
                id = 0,
                amountMl = 200,
                timestamp = System.currentTimeMillis()
            )
        )

        val result = repository.getWaterIntakeInRange(start, end)

        assertEquals(1, result.size)
        assertEquals(200, result[0].amountMl)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
