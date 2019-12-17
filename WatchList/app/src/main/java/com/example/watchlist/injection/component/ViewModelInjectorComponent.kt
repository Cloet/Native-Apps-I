package com.example.watchlist.injection.component

import com.example.watchlist.App
import com.example.watchlist.injection.module.DatabaseModule
import com.example.watchlist.injection.module.NetworkModule
import com.example.watchlist.persistence.SavedSerieRepository
import com.example.watchlist.ui.*
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [NetworkModule::class, DatabaseModule::class])
interface ViewModelInjectorComponent {

    fun inject(app: App)
    fun inject(mainViewModel: MainViewModel)
    fun inject(addSeriesViewModel: AddSeriesViewModel)
    fun inject(seriesViewModel: SeriesViewModel)
    fun inject(episodeViewModel: EpisodeViewModel)
    fun inject(seriesDetailViewModel: SeriesDetailViewModel)
    fun inject(savedSeriesRepository: SavedSerieRepository)

    @Component.Builder
    interface  Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
        fun databaseModule(databaseModule: DatabaseModule): Builder
    }

}