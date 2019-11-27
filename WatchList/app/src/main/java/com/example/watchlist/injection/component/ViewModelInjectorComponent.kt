package com.example.watchlist.injection.component

import com.example.watchlist.App
import com.example.watchlist.activities.MainActivity
import com.example.watchlist.injection.module.DatabaseModule
import com.example.watchlist.injection.module.NetworkModule
import com.example.watchlist.persistence.SavedSerieRepository
import com.example.watchlist.ui.AddSeriesViewModel
import com.example.watchlist.ui.MainViewModel
import com.example.watchlist.ui.SavedSeriesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [NetworkModule::class, DatabaseModule::class])
interface ViewModelInjectorComponent {

    fun inject(app: App)
    fun inject(mainViewModel: MainViewModel)
    fun inject(addSeriesViewModel: AddSeriesViewModel)
    fun inject(savedSeriesViewModel: SavedSeriesViewModel)
    fun inject(savedSeriesRepository: SavedSerieRepository)

    @Component.Builder
    interface  Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
    }

}