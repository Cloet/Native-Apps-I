package com.example.watchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.persistence.SavedSerieRepository
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class SeriesViewModel : ViewModel() {


    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    init {
        App.component.inject(this)
    }

    fun getAllSeries(): LiveData<List<SavedSerie>> {
        return savedSerieRepository.getAllSavedSeries()
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