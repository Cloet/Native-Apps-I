package com.example.watchlist.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.watchlist.dao.SavedSerieDao
import com.example.watchlist.model.SavedSerie

/**
 * Room database for entity [SavedSerie]
 * @see [RoomDatabase]
 * */
@Database(entities = arrayOf(SavedSerie::class), version=1, exportSchema = false)
abstract class SavedSeriesDatabase : RoomDatabase() {
    abstract fun savedSerieDao(): SavedSerieDao

    companion object {
        @Volatile
        private var INSTANCE: SavedSeriesDatabase? = null

        /**
         * Returns an instance of [SavedSeriesDatabase].
         * This is a singleton with lazy loading.
         * @param context [Context]
         * @return [SavedSeriesDatabase]
         * */
        fun getDatabase(context: Context): SavedSeriesDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedSeriesDatabase::class.java,
                    "WatchList_database"
                )
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}