package com.example.radiotest.domain

import kotlinx.coroutines.flow.Flow

interface CoordinateRepository {
    suspend fun saveCoordinate(coordinate: Coordinate)
    fun getCoordinate(): Flow<List<Coordinate>>
}