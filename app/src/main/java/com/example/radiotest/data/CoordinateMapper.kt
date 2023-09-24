package com.example.radiotest.data

import com.example.radiotest.data.database.model.CoordinateDb
import com.example.radiotest.domain.Coordinate
import javax.inject.Inject

class CoordinateMapper @Inject constructor() {
    fun mapEntityToDb(coordinate: Coordinate): CoordinateDb {
        return CoordinateDb(latitude = coordinate.latitude, longitude = coordinate.longitude)
    }

    fun mapDbModelToEntity(listCoordinate: List<CoordinateDb>): List<Coordinate>{
        return listCoordinate.map {coordinateDb ->
            Coordinate(latitude = coordinateDb.latitude, longitude = coordinateDb.longitude)
        }
    }
}