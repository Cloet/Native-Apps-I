package com.example.watchlist.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.test.platform.app.InstrumentationRegistry
import com.example.watchlist.TestUtils
import com.example.watchlist.persistence.SavedSeriesDatabase
import org.jetbrains.anko.doAsync
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class SavedSerieDaoTest {


    private lateinit var savedSerieDatabase: SavedSeriesDatabase

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        savedSerieDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().context, SavedSeriesDatabase::class.java).allowMainThreadQueries().build()
    }

    @After
    fun closeDb() {
        savedSerieDatabase.close()
    }

    @Test
    fun insertSerieSavesData() {
        val series = TestUtils.createMockSeries()
        series.forEach {
            savedSerieDatabase.savedSerieDao().insert(it)
        }

        val retrievedSeries = TestUtils.getValue(savedSerieDatabase.savedSerieDao().getAllSavedSeries())
        assert(retrievedSeries.isNotEmpty())
    }

    @Test
    fun getSeriesDatabaseRetrievesData() {
        val series = TestUtils.createMockSeries()
        series.forEach {
            savedSerieDatabase.savedSerieDao().insert(it)
        }

        val retrievedSeries = TestUtils.getValue(savedSerieDatabase.savedSerieDao().getAllSavedSeries())
        assert(retrievedSeries == series.sortedWith(compareBy({it.savedSerieId}, {it.savedSerieId})))
    }

    @Test
    fun clearSeriesDatabase() {
        val series = TestUtils.createMockSeries()
        series.forEach {
            savedSerieDatabase.savedSerieDao().insert(it)
        }

        savedSerieDatabase.savedSerieDao().deleteAllSavedSeries()
        val retrievedSeries = TestUtils.getValue(savedSerieDatabase.savedSerieDao().getAllSavedSeries())
        assert(retrievedSeries.isEmpty())

    }

}