package com.example.watchlist.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watchlist.dao.SavedSerieDao
import com.example.watchlist.model.SavedSerie

class SavedSerieRepository(private val savedSerieDao: SavedSerieDao) {


    @WorkerThread
    fun insert(savedSerie: SavedSerie) {
        savedSerieDao.insert(savedSerie)
    }

    @WorkerThread
    fun delete(savedSerie: SavedSerie) {
        savedSerieDao.delete(savedSerie)
    }

    @WorkerThread
    fun deleteAllSavedSeries() {
        savedSerieDao.deleteAllSavedSeries()
    }

    @WorkerThread
    fun getAllSavedSeries(): LiveData<List<SavedSerie>> {
        return savedSerieDao.getAllSavedSeries()
    }

    @WorkerThread
    fun checkIfSeriesExists(savedSerie: SavedSerie): Boolean {
        val count = savedSerieDao.checkIfSerieExistsInDatabase(savedSerie.savedSerieId)
        if (count == 0) return false

        return true
    }

}