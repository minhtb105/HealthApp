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
    fun insertAndGetAllWaterIntake() = runBlocking {
        val entity = WaterIntakeEntity(
            amountMl = 250,
            timestamp = 123456L
        )

        dao.insertWaterIntake(entity)

        val list = dao.getAllWaterIntake()
        assertEquals(1, list.size)
        assertEquals(250, list[0].amountMl)
    }

    @After
    fun tearDown() {
        db.close()
    }
}
