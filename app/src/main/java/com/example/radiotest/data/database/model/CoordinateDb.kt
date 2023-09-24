package com.example.radiotest.data.database.model

import androidx.room.Entity

@Entity(tableName = "coordinate_table", primaryKeys = ["latitude", "longitude"])
data class CoordinateDb(
    val latitude: String,
    val longitude: String
)