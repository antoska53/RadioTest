package com.example.radiotest.presentation.coordinates

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.radiotest.domain.Coordinate

class CoordinatesAdapter: RecyclerView.Adapter<CoordinatesViewHolder>() {
    private val listCoordinates = mutableListOf<Coordinate>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordinatesViewHolder {
        return CoordinatesViewHolder.createViewHolder(parent)
    }

    override fun getItemCount() = listCoordinates.size

    override fun onBindViewHolder(holder: CoordinatesViewHolder, position: Int) {
        holder.onBind(listCoordinates[position], position)
    }

    fun addCoordinates(coordinates: List<Coordinate>){
        listCoordinates.clear()
        listCoordinates.addAll(coordinates)
        notifyItemRangeInserted(0, listCoordinates.size)
    }
}