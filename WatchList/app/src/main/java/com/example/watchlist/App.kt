package com.example.watchlist

import android.app.Application
import com.example.watchlist.injection.component.DaggerDatabaseComponent
import com.example.watchlist.injection.component.DatabaseComponent
import com.example.watchlist.injection.module.DatabaseModule

class App: Application() {
    companion object {
        lateinit var component: DatabaseComponent
    }

    override fun onCreate() {
        super.onCreate()
        component = DaggerDatabaseComponent
            .builder()
            .databaseModule(DatabaseModule(this))
            .build()
    }
}