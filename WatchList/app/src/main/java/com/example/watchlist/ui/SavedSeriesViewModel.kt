package com.example.watchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.persistence.SavedSerieRepository
import org.jetbrains.anko.doAsync
import javax.inject.Inject


class SavedSeriesViewModel : ViewModel() {


    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    init {
        App.component.inject(this)
        insertSampleData()
    }

    fun getAllSeries(): LiveData<List<SavedSerie>> {
        return savedSerieRepository.getAllSavedSeries()
    }

    fun insertSerie(savedSerie: SavedSerie) {
        savedSerieRepository.insert(savedSerie)
    }

    fun insertSampleData() {
        doAsync {
            insertSerie(SavedSerie("1","Test","This is an overview","good","DONE","","Disney+",""))
            insertSerie(SavedSerie("2","Mandalorian","This is an overview","good","DONE","","Disney+",""))
            insertSerie(SavedSerie("3","Star Wars","This is an overview","good","DONE","","Disney+",""))
            insertSerie(SavedSerie("4","House","This is an overview","good","DONE","","Disney+",""))
        }
    }

}