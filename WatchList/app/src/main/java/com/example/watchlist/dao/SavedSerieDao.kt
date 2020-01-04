package com.example.watchlist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watchlist.model.SavedSerie


/**
 * Data Access Object for [SavedSerie] for Room database operations
 * @see Dao
 * */
@Dao
interface SavedSerieDao {

    /**
     * Retrieves all [SavedSerie] objects.
     * @return [LiveData] list of [SavedSerie]
     * */
    @Query("SELECT * from Savedserie_table")
    fun getAllSavedSeries(): LiveData<List<SavedSerie>>

    /**
     * Insert a [SavedSerie], replace any existing value.
     * @param savedSerie [SavedSerie]
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedSerie: SavedSerie)

    /**
     * Delete given [savedSerie] from the room database.
     * @param savedSerie [SavedSerie]
     * */
    @Delete
    fun delete(savedSerie: SavedSerie)

    /**
     * Update the rating for a [SavedSerie] with given id [savedSerieId] to the given [rating]
     * @param savedSerieId [String]
     * @param rating [Float]
     * */
    @Query("UPDATE Savedserie_table set rating = :rating where savedSerieId = :savedSerieId")
    fun updateRatingSerie(savedSerieId: String, rating: Float)

    /**
     * Delete all [SavedSerie] objects from the room database.
     * */
    @Query("DELETE FROM Savedserie_table")
    fun deleteAllSavedSeries()

    /**
     * Retrieve [SavedSerie] with given [savedSerieId] id.
     * @param savedSerieId [String]
     * @return [SavedSerie]
     * */
    @Query("SELECT * from savedserie_table where savedSerieId = :savedSerieId")
    fun getSavedSerie(savedSerieId: String): SavedSerie?

    /**
     * Checks if a [SavedSerie] with given id [savedSerieId] exists in the room database.
     * @param savedSerieId [String]
     * @return [Int]
     * */
    @Query("SELECT COUNT(*) from savedserie_table where savedSerieId = :savedSerieId")
    fun checkIfSerieExistsInDatabase(savedSerieId: String): Int

    /**
     * Retrieve all [SavedSerie] objects where the status is 'Continuing'
     * @return [LiveData] list of [SavedSerie]
     * */
    @Query("Select * from savedserie_table where status = 'Continuing'")
    fun getAllContinuingSeries(): LiveData<List<SavedSerie>>

    /**
     * Get total amount of [SavedSerie] in the room database.
     * @return [LiveData] of [Int]
     * */
    @Query("Select COUNT(*) from savedserie_table")
    fun getTotalAmountOfSeriesSaved(): LiveData<Int>

}