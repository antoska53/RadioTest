package com.example.radiotest.data

import com.example.radiotest.data.database.CoordinateDao
import com.example.radiotest.domain.Coordinate
import com.example.radiotest.domain.CoordinateRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CoordinateRepositoryImpl @Inject constructor(
    private val coordinateDao: CoordinateDao,
    private val mapper: CoordinateMapper
) : CoordinateRepository {
    override suspend fun saveCoordinate(coordinate: Coordinate) {
        coordinateDao.insertCoordinate(mapper.mapEntityToDb(coordinate))
    }

    override fun getCoordinate(): Flow<List<Coordinate>> {
        return coordinateDao.getCoordinates().map {
            mapper.mapDbModelToEntity(it)
        }
    }
}