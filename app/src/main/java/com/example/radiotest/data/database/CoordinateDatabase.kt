package com.example.radiotest.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.radiotest.data.database.model.CoordinateDb

@Database(entities = [CoordinateDb::class], version = 1)
abstract class CoordinateDatabase : RoomDatabase() {
    abstract fun coordinateDao(): CoordinateDao
}