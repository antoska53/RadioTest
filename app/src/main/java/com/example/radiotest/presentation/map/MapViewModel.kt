package com.example.radiotest.presentation.map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.radiotest.domain.Coordinate
import com.example.radiotest.domain.GetCoordinateUseCase
import com.example.radiotest.domain.SaveCoordinateUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MapViewModel (
    private val saveCoordinateUseCase: SaveCoordinateUseCase,
    getCoordinateUseCase: GetCoordinateUseCase
): ViewModel() {
    val listCoordinate = getCoordinateUseCase.getCoordinate().asLiveData()


    fun saveCoordinate(lat: String, lon: String ){
        viewModelScope.launch(Dispatchers.IO) {
            saveCoordinateUseCase.saveCoordinate(Coordinate(lat, lon))
        }
    }

    class Factory @Inject constructor(
        private val saveCoordinateUseCase: SaveCoordinateUseCase,
        private val getCoordinateUseCase: GetCoordinateUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MapViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MapViewModel(
                    saveCoordinateUseCase, getCoordinateUseCase
                ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}