package com.example.radiotest.di

import com.example.radiotest.data.CoordinateRepositoryImpl
import com.example.radiotest.domain.CoordinateRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {
    @Binds
    fun bindCoordinateRepository(repositoryImpl: CoordinateRepositoryImpl): CoordinateRepository
}