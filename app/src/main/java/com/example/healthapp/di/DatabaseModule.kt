package com.example.healthapp.di

import android.content.Context
import androidx.room.Room
import com.example.healthapp.data.local.HealthDatabase
import com.example.healthapp.data.local.dao.WaterIntakeDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): HealthDatabase {
        return Room.databaseBuilder(
            context,
            HealthDatabase::class.java,
            "health_db"
        ).build()
    }

    @Provides
    fun provideWaterDao(
        database: HealthDatabase
    ): WaterIntakeDao {
        return database.waterIntakeDao()
    }
}
