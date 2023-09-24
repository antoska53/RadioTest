package com.example.radiotest.presentation.coordinates

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.radiotest.domain.Coordinate
import com.example.radiotest.domain.GetCoordinateUseCase
import javax.inject.Inject

class CoordinateListViewModel(
    getCoordinateUseCase: GetCoordinateUseCase
): ViewModel() {
    val listCoordinate: LiveData<List<Coordinate>> = getCoordinateUseCase.getCoordinate().asLiveData()


    class Factory @Inject constructor(
        private val getCoordinateUseCase: GetCoordinateUseCase
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CoordinateListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CoordinateListViewModel(
                    getCoordinateUseCase
                ) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }
}