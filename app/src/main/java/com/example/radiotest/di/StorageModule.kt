package com.example.radiotest.di

import android.content.Context
import androidx.room.Room
import com.example.radiotest.data.database.CoordinateDao
import com.example.radiotest.data.database.CoordinateDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class StorageModule(private val appContext: Context) {

    @Singleton
    @Provides
    fun provideCoordinateDao(): CoordinateDao{
        return Room.databaseBuilder(
            appContext,
            CoordinateDatabase::class.java,
            "coordinate_database"
        )
            .build()
            .coordinateDao()
    }
}