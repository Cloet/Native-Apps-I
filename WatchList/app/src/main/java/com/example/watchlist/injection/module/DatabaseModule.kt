package com.example.watchlist.injection.module

import android.app.Application
import android.content.Context
import com.example.watchlist.dao.SavedSerieDao
import com.example.watchlist.persistence.SavedSerieRepository
import com.example.watchlist.persistence.SavedSeriesDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Provides the Database Service implementation
 * @param application [Application], application context.
 * */
@Module
class DatabaseModule(private val application: Application) {

    /**
     * Shows how to create a [SavedSerieRepository]
     * @param savedSerieDao [SavedSerieDao]
     * @return [SavedSerieRepository]
     * */
    @Provides
    @Singleton
    internal fun provideSavedSerieRepository(savedSerieDao: SavedSerieDao): SavedSerieRepository {
        return SavedSerieRepository(savedSerieDao)
    }

    /**
     * Shows how to create a [SavedSerieDao]
     * @param savedSeriesDatabase [SavedSeriesDatabase]
     * @return [SavedSerieDao]
     * */
    @Provides
    @Singleton
    internal fun provideSavedSeriesDao(savedSeriesDatabase: SavedSeriesDatabase): SavedSerieDao {
        return savedSeriesDatabase.savedSerieDao()
    }

    /**
     * Shows how to create a [SavedSeriesDatabase]
     * @param context [Context]
     * @return [SavedSeriesDatabase]
     * */
    @Provides
    @Singleton
    internal fun provideSavedSeriesDatabase(context: Context): SavedSeriesDatabase {
        return SavedSeriesDatabase.getDatabase(context)
    }


    /**
     * Creates the application context.
     * @return [Context]
     * */
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return application
    }

}