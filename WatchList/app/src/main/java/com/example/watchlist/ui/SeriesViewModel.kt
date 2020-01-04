package com.example.watchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.persistence.SavedSerieRepository
import kotlinx.coroutines.*
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class SeriesViewModel : ViewModel() {

    /**
     * Properties
     * */
    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    private val _retrieving = MutableLiveData<Boolean>()
    val retrieving: LiveData<Boolean>
        get() = _retrieving


    /**
     * Constructor.
     * Dagger dependency injection.
     */
    init {
        App.component.inject(this)
    }

    /**
     * Gets all [SavedSerie] from room database
     * */
    fun getAllSeries(): LiveData<List<SavedSerie>> {
        this._retrieving.value = true
        val series = savedSerieRepository.getAllSavedSeries()
        this._retrieving.value = false
        return series
    }

    /**
     * Insert a [SavedSerie] into the room database
     * @param savedSerie [SavedSerie]
     * */
    fun insertSerie(savedSerie: SavedSerie) {
        doAsync {
            savedSerieRepository.insert(savedSerie)
        }
    }

    /**
     * Delete a [SavedSerie] from the room database
     * @param savedSerie [SavedSerie]
     * */
    fun deleteSerie(savedSerie: SavedSerie) {
        doAsync {
            savedSerieRepository.delete(savedSerie)
        }
    }

}