package com.example.healthapp
import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import com.example.healthapp.data.local.entity.WaterIntakeEntity
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import org.junit.Assert.assertEquals


@RunWith(AndroidJUnit4::class)
class WaterIntakeDaoTest {

    private lateinit var db: HealthDatabase
    private lateinit var dao: WaterIntakeDao

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            HealthDatabase::class.java
        ).build()

        dao = db.waterIntakeDao()
    }

    @Test
    fun insertAndGetWaterIntakeForDay_returnsOnlyDataInRange() = runBlocking {
        val now = System.currentTimeMillis()

        val entity = WaterIntakeEntity(
            amountMl = 250,
            timestamp = now
        )

        dao.insertWaterIntake(entity)

        val start = now - 1_000
        val end = now + 1_000

        val list = dao.getWaterIntakeForDay(start, end)

        assertEquals(1, list.size)
        assertEquals(250, list.first().amountMl)
    }

    @Test
    fun getWaterIntakeSince_returnsRecentDataOnly() = runBlocking {
        val oldTime = System.currentTimeMillis() - 10_000
        val recentTime = System.currentTimeMillis()

        dao.insertWaterIntake(
            WaterIntakeEntity(
                amountMl = 100,
                timestamp = oldTime
            )
        )

        dao.insertWaterIntake(
            WaterIntakeEntity(
                amountMl = 300,
                timestamp = recentTime
            )
        )

        val result = dao.getWaterIntakeSince(
            startTime = System.currentTimeMillis() - 5_000
        )

        assertEquals(1, result.size)
        assertEquals(300, result.first().amountMl)
    }

    @Test
    fun getWaterIntakeInRange_returnsCorrectData() = runBlocking {
        val baseTime = System.currentTimeMillis()

        dao.insertWaterIntake(
            WaterIntakeEntity(
                amountMl = 150,
                timestamp = baseTime
            )
        )

        val result = dao.getWaterIntakeInRange(
            from = baseTime - 1_000,
            to = baseTime + 1_000
        )

        assertEquals(1, result.size)
        assertEquals(150, result[0].amountMl)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
