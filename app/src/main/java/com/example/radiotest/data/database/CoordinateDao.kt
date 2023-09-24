package com.example.radiotest.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.radiotest.data.database.model.CoordinateDb
import kotlinx.coroutines.flow.Flow

@Dao
interface CoordinateDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCoordinate(coordinate: CoordinateDb)

    @Query("SELECT * FROM coordinate_table")
    fun getCoordinates(): Flow<List<CoordinateDb>>
}