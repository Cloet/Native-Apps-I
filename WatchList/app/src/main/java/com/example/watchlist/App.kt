package com.example.watchlist

import android.app.Application
import com.example.watchlist.injection.component.DaggerViewModelInjectorComponent
import com.example.watchlist.injection.component.ViewModelInjectorComponent
import com.example.watchlist.injection.module.DatabaseModule
import com.example.watchlist.injection.module.NetworkModule


class App: Application() {
    companion object {
        lateinit var component: ViewModelInjectorComponent
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerViewModelInjectorComponent
            .builder()
            .networkModule(NetworkModule())
            .databaseModule(DatabaseModule(this))
            .build()
        application = this
    }
}