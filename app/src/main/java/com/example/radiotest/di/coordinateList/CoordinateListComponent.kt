package com.example.radiotest.di.coordinateList

import com.example.radiotest.presentation.coordinates.CoordinateListFragment
import dagger.Subcomponent

@CoordinateListScope
@Subcomponent
interface CoordinateListComponent {
    fun inject(fragment: CoordinateListFragment)
}