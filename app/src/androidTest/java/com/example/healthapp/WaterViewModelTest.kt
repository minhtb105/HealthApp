package com.example.healthapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.local.entity.UserProfileEntity
import com.example.healthapp.data.repository.WaterRepositoryImpl
import com.example.healthapp.ui.water.WaterUiState
import com.example.healthapp.ui.water.WaterViewModel
import com.example.healthapp.utils.TimeUtils
import com.example.healthapp.utils.TimeProvider
import com.example.healthapp.fake.FakeSessionManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.flow.filterIsInstance
import org.junit.*
import org.junit.runner.RunWith


class FakeTimeProvider(private val fixedTime: Long) : TimeProvider {
    override fun now(): Long = fixedTime
}

@RunWith(AndroidJUnit4::class)
class WaterViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var db: HealthDatabase
    private lateinit var repository: WaterRepositoryImpl
    private lateinit var viewModel: WaterViewModel
    private lateinit var sessionManager: FakeSessionManager

    @Before
    fun setup() = runBlocking {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        val fakeTimeProvider = FakeTimeProvider(1_720_000_000_000L)
        sessionManager = FakeSessionManager()

        repository = WaterRepositoryImpl(
            dao = db.waterIntakeDao(),
            timeUtils = TimeUtils(fakeTimeProvider),
            sessionManager = sessionManager
        )

        viewModel = WaterViewModel(
            repository = repository,
            timeProvider = fakeTimeProvider
        )

        val userId = sessionManager.currentUserId
            ?: throw IllegalStateException("User not logged in")

        db.userProfileDao().upsert(
            UserProfileEntity(
                userId = userId,
                gender = "unknown",
                birthDate = "1990-01-01",
                heightCm = 0,
            )
        )
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addWater_updates_uiState_successfully() = runBlocking {
        // WHEN
        viewModel.addWater(250)

        val state = viewModel.uiState
            .filterIsInstance<WaterUiState.Success>()
            .first()

        // THEN
        Assert.assertEquals(250, state.totalAmountMl)
        Assert.assertEquals(1, state.items.size)
    }
}
