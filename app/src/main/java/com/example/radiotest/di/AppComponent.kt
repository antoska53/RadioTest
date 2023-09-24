package com.example.radiotest.di

import com.example.radiotest.di.coordinateList.CoordinateListComponent
import com.example.radiotest.di.map.MapComponent
import com.example.radiotest.di.tcp.TcpComponent
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DomainModule::class, StorageModule::class])
interface AppComponent {
    fun getMapComponent(): MapComponent
    fun getCoordinateListComponent(): CoordinateListComponent
    fun getTcpComponent(): TcpComponent
}