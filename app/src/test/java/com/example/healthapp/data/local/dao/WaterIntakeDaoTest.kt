package com.example.healthapp

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [34],
    manifest = Config.NONE
)
class WaterIntakeDaoTest {

    private lateinit var db: HealthDatabase
    private lateinit var dao: WaterIntakeDao

    companion object {
        // Fake fixed timestamp to avoid flaky tests
        private const val BASE_TIME = 1_700_000_000_000L
    }

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        )
            .allowMainThreadQueries() // OK for tests only
            .build()

        dao = db.waterIntakeDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    // ----------------------------------------------------
    // getAllWaterIntake
    // ----------------------------------------------------

    @Test
    fun getAllWaterIntake_ordersByTimestampDesc() = runBlocking {
        // Given
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 100, timestamp = BASE_TIME)
        )
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 200, timestamp = BASE_TIME + 1_000)
        )

        // When
        val result = dao.getAllWaterIntake()

        // Then
        assertEquals(2, result.size)
        assertEquals(200, result[0].amountMl)
        assertEquals(100, result[1].amountMl)
    }

    // ----------------------------------------------------
    // getWaterIntakeForDay
    // ----------------------------------------------------

    @Test
    fun getWaterIntakeForDay_returnsOnlyDataInRange_orderedDesc() = runBlocking {
        // Given
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 100, timestamp = BASE_TIME - 10_000)
        )
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 250, timestamp = BASE_TIME)
        )
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 300, timestamp = BASE_TIME + 10_000)
        )

        // When
        val result = dao.getWaterIntakeForDay(
            startOfDay = BASE_TIME - 1_000,
            endOfDay = BASE_TIME + 1_000
        )

        // Then
        assertEquals(1, result.size)
        assertEquals(250, result.first().amountMl)
    }

    // ----------------------------------------------------
    // getWaterIntakeSince
    // ----------------------------------------------------

    @Test
    fun getWaterIntakeSince_returnsOnlyRecentData() = runBlocking {
        // Given
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 100, timestamp = BASE_TIME - 10_000)
        )
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 300, timestamp = BASE_TIME)
        )

        // When
        val result = dao.getWaterIntakeSince(
            startTime = BASE_TIME - 5_000
        )

        // Then
        assertEquals(1, result.size)
        assertEquals(300, result.first().amountMl)
    }

    // ----------------------------------------------------
    // getWaterIntakeInRange
    // ----------------------------------------------------

    @Test
    fun getWaterIntakeInRange_returnsCorrectData() = runBlocking {
        // Given
        dao.insertWaterIntake(
            WaterIntakeEntity(amountMl = 150, timestamp = BASE_TIME)
        )

        // When
        val result = dao.getWaterIntakeInRange(
            from = BASE_TIME - 1_000,
            to = BASE_TIME + 1_000
        )

        // Then
        assertEquals(1, result.size)
        assertEquals(150, result.first().amountMl)
    }
}
