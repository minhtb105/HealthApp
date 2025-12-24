package com.example.healthapp

import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.repository.HealthRepositoryImpl
import com.example.healthapp.ui.water.WaterUiState
import com.example.healthapp.ui.water.WaterViewModel
import com.example.healthapp.utils.TimeUtils
import com.example.healthapp.utils.TimeProvider
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
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
    private lateinit var repository: HealthRepositoryImpl
    private lateinit var viewModel: WaterViewModel

    @Before
    fun setup() {
        val context: Context = ApplicationProvider.getApplicationContext()

        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()

        val fakeTimeProvider = FakeTimeProvider(1_720_000_000_000L)

        repository = HealthRepositoryImpl(
            dao = db.waterIntakeDao(),
            timeUtils = TimeUtils(fakeTimeProvider)
        )

        viewModel = WaterViewModel(
            repository = repository,
            timeProvider = fakeTimeProvider
        )

    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun addWater_updates_uiState_successfully() = runBlocking {
        // GIVEN
        viewModel.addWater(250)

        // wait coroutine handle
        delay(300)

        // WHEN
        val state = viewModel.uiState.first()

        // THEN
        Assert.assertTrue(state is WaterUiState.Success)

        val success = state as WaterUiState.Success
        Assert.assertEquals(250, success.totalAmountMl)
        Assert.assertEquals(1, success.items.size)
    }
}
