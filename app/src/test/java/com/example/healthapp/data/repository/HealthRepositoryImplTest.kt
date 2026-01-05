package com.example.healthapp.data.repository

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.local.entity.UserProfileEntity
import com.example.healthapp.domain.model.WaterIntake
import com.example.healthapp.domain.repository.WaterRepository
import com.example.healthapp.domain.session.SessionManager
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import com.example.healthapp.utils.TimeProvider
import com.example.healthapp.utils.TimeUtils
import com.example.healthapp.fake.FakeSessionManager



class FakeTimeProvider(private val fixedTime: Long) : TimeProvider {
    override fun now() = fixedTime
}

@RunWith(RobolectricTestRunner::class)
@Config(
    sdk = [34],
    manifest = Config.NONE
)
class HealthRepositoryImplTest {

    private lateinit var db: HealthDatabase
    private lateinit var repository: WaterRepository
    private lateinit var sessionManager: SessionManager

    companion object {
        private const val FIXED_TIME = 1_720_000_000_000L
    }

    @Before
    fun setup() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()

        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        ).build()

        val timeUtils = TimeUtils(FakeTimeProvider(FIXED_TIME))
        val dao = db.waterIntakeDao()
        sessionManager = FakeSessionManager()

        repository = WaterRepositoryImpl(
            dao = dao,
            timeUtils = timeUtils,
            sessionManager = sessionManager
        )

        val userId = sessionManager.requireUserId()

        db.userProfileDao().upsert(
            UserProfileEntity(
                userId = userId,
                gender = "unknown",
                birthDate = "1990-01-01",
                heightCm = 0,
            )
        )
    }

    @Test
    fun addAndGetTodayWaterIntake_returnsOnlyTodayData() = runBlocking {
        // insert today's data
        repository.addWaterIntake(
            WaterIntake(
                id = 0,
                amountMl = 300,
                timestamp = FIXED_TIME
            )
        )

        val todayList = repository.getTodayWaterIntake()

        assertEquals(1, todayList.size)
        assertEquals(300, todayList.first().amountMl)
    }

    @Test
    fun getWaterIntakeInRange_returnsCorrectData() = runBlocking {
        val start = FIXED_TIME - 60_000
        val end = FIXED_TIME + 60_000

        repository.addWaterIntake(
            WaterIntake(
                id = 0,
                amountMl = 200,
                timestamp = FIXED_TIME
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
