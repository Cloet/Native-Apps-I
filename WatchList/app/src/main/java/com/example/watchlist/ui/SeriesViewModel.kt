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


    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    private val _retrieving = MutableLiveData<Boolean>()
    val retrieving: LiveData<Boolean>
        get() = _retrieving


    init {
        App.component.inject(this)
    }

    fun getAllSeries(): LiveData<List<SavedSerie>> {
        this._retrieving.value = true
        val series = savedSerieRepository.getAllSavedSeries()
        this._retrieving.value = false
        return series
    }

    fun insertSerie(savedSerie: SavedSerie) {
        doAsync {
            savedSerieRepository.insert(savedSerie)
        }
    }

    fun deleteSerie(savedSerie: SavedSerie) {
        doAsync {
            savedSerieRepository.delete(savedSerie)
        }
    }

}