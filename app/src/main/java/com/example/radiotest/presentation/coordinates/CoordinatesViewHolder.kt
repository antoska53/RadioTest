package com.example.radiotest.presentation.coordinates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.radiotest.databinding.CoordinatesViewholderBinding
import com.example.radiotest.domain.Coordinate

class CoordinatesViewHolder(private val binding: CoordinatesViewholderBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun onBind(coordinate: Coordinate, position: Int) {
        binding.textviewCoordinate.text = "${coordinate.latitude}°, ${coordinate.longitude}°"
        binding.textviewNumber.text = "Точка ${(position + 1)}"
    }

    companion object {
        fun createViewHolder(parent: ViewGroup): CoordinatesViewHolder {
            return CoordinatesViewHolder(
                CoordinatesViewholderBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false)
            )
        }
    }
}