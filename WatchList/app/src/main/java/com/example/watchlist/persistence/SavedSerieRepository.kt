package com.example.watchlist.persistence

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.example.watchlist.dao.SavedSerieDao
import com.example.watchlist.model.SavedSerie

/**
 * Repository for [SavedSerie] for Room database operations.
 * @constructor Creates a [SavedSerieRepository]
 * @property savedSerieDao [SavedSerieDao]
 * */
class SavedSerieRepository(private val savedSerieDao: SavedSerieDao) {

    /**
     * Inserts [SavedSerie] in Room database.
     * @param savedSerie [SavedSerie] to be inserted
     * @see SavedSerieDao
     * */
    @WorkerThread
    fun insert(savedSerie: SavedSerie) {
        savedSerieDao.insert(savedSerie)
    }

    /**
     * Deletes [SavedSerie] from Room database.
     * @param savedSerie [SavedSerie] to be deleted.
     * @see savedSerieDao
     * */
    @WorkerThread
    fun delete(savedSerie: SavedSerie) {
        savedSerieDao.delete(savedSerie)
    }

    /**
     * Deletes all [SavedSerie] from Room database.
     * @see savedSerieDao
     * */
    @WorkerThread
    fun deleteAllSavedSeries() {
        savedSerieDao.deleteAllSavedSeries()
    }

    /**
     * Retrieves all [SavedSerie] from room database.
     * @return [LiveData] list of [SavedSerie]
     * @see savedSerieDao
     * */
    @WorkerThread
    fun getAllSavedSeries(): LiveData<List<SavedSerie>> {
        return savedSerieDao.getAllSavedSeries()
    }

    /**
     * Updates the rating of the given [SavedSerie]
     * @param savedSerie [SavedSerie]
     * */
    @WorkerThread
    fun updateSavedSerieRating(savedSerie: SavedSerie) {
        savedSerieDao.updateRatingSerie(savedSerie.savedSerieId, savedSerie.rating)
    }

    /**
     * Retrieves a [SavedSerie] with given Id
     * @param savedSerie [SavedSerie]
     * @return [SavedSerie]
     * */
    @WorkerThread
    fun getSerieWithId(savedSerie: SavedSerie): SavedSerie? {
        return savedSerieDao.getSavedSerie(savedSerie.savedSerieId)
    }

    /**
     * Retrieves all [SavedSerie] objects from database where status = 'Continuing'
     * @return [LiveData] list of [SavedSerie]
     * */
    @WorkerThread
    fun getAllContinuingSeries() : LiveData<List<SavedSerie>> {
        return savedSerieDao.getAllContinuingSeries()
    }

    /**
     * Retrieves total amount of [SavedSerie] objects in the database.
     * @return [LiveData] of [Int]
     * */
    @WorkerThread
    fun getTotalAmountSavedSeries(): LiveData<Int> {
        return savedSerieDao.getTotalAmountOfSeriesSaved()
    }

    /**
     * Checks if a series already exists in the room database.
     * @param savedSerie [SavedSerie]
     * @return [Boolean]
     * */
    @WorkerThread
    fun checkIfSeriesExists(savedSerie: SavedSerie): Boolean {
        val count = savedSerieDao.checkIfSerieExistsInDatabase(savedSerie.savedSerieId)
        if (count == 0) return false

        return true
    }

}