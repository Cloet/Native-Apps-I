package com.example.watchlist.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.watchlist.model.SavedSerie


@Dao
interface SavedSerieDao {

    @Query("SELECT * from Savedserie_table")
    fun getAllSavedSeries(): LiveData<List<SavedSerie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(savedSerie: SavedSerie)

    @Delete
    fun delete(savedSerie: SavedSerie)

    @Query("DELETE FROM Savedserie_table")
    fun deleteAllSavedSeries()

    @Query("SELECT * from savedserie_table where savedSerieId = :savedSerieId")
    fun getSavedSerie(savedSerieId: String): SavedSerie?

    @Query("SELECT COUNT(*) from savedserie_table where savedSerieId = :savedSerieId")
    fun checkIfSerieExistsInDatabase(savedSerieId: String): Int

}