package com.example.radiotest.data

import kotlinx.serialization.Serializable

@Serializable
data class CoordinateDto (
    val point: PointDto
)

@Serializable
data class PointDto (
    val lat: String,
    val long: String
)