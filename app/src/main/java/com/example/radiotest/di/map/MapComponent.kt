package com.example.radiotest.di.map

import com.example.radiotest.presentation.map.MapFragment
import dagger.Subcomponent

@MapScope
@Subcomponent
interface MapComponent {
    fun inject(fragment: MapFragment)
}