package com.example.radiotest.presentation

import android.app.Application
import com.example.radiotest.BuildConfig
import com.example.radiotest.di.AppComponent
import com.example.radiotest.di.DaggerAppComponent
import com.example.radiotest.di.StorageModule
import com.yandex.mapkit.MapKitFactory

class App: Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder().storageModule(StorageModule(applicationContext)).build()
        MapKitFactory.setApiKey(BuildConfig.API_KEY)
    }
}