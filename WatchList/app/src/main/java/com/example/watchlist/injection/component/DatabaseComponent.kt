package com.example.watchlist.injection.component

import com.example.watchlist.App
import com.example.watchlist.activities.MainActivity
import com.example.watchlist.injection.module.DatabaseModule
import com.example.watchlist.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DatabaseModule::class])
interface DatabaseComponent {
    fun inject(app: App)
}