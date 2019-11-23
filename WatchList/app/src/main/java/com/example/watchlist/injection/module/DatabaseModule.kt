package com.example.watchlist.injection.module

import android.app.Application
import android.content.Context
import com.example.watchlist.dao.SavedSerieDao
import com.example.watchlist.persistence.SavedSerieRepository
import com.example.watchlist.persistence.SavedSeriesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class DatabaseModule(private val application: Application) {

    @Provides
    @Singleton
    internal fun provideSavedSerieRepository(savedSerieDao: SavedSerieDao): SavedSerieRepository {
        return SavedSerieRepository(savedSerieDao)
    }

    @Provides
    @Singleton
    internal fun provideSavedSeriesDao(savedSeriesDatabase: SavedSeriesDatabase): SavedSerieDao {
        return savedSeriesDatabase.savedSerieDao()
    }

    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}