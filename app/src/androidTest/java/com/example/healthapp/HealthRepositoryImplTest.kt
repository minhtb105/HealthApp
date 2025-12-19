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
    fun addAndGetWaterIntake_domainModelReturned() = runBlocking {
        repository.addWaterIntake(
            WaterIntake(
                id = 0,
                amountMl = 300,
                timestamp = 999L
            )
        )

        val list = repository.getAllWaterIntake()

        assertEquals(1, list.size)
        assertEquals(300, list[0].amountMl)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
