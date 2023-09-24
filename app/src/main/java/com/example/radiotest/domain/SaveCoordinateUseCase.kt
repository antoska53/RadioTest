package com.example.radiotest.domain

import javax.inject.Inject

class SaveCoordinateUseCase @Inject constructor(
    private val repository: CoordinateRepository
) {
    suspend fun saveCoordinate(coordinate: Coordinate) = repository.saveCoordinate(coordinate)
}