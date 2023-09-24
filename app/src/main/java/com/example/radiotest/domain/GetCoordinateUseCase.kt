package com.example.radiotest.domain

import javax.inject.Inject

class GetCoordinateUseCase @Inject constructor(
    private val repository: CoordinateRepository
) {
    fun getCoordinate() = repository.getCoordinate()
}