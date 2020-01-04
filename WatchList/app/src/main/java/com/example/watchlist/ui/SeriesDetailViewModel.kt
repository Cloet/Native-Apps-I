package com.example.watchlist.ui

import androidx.databinding.BaseObservable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.persistence.SavedSerieRepository
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class SeriesDetailViewModel: ViewModel() {

    /**
     * Properties
     * */
    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    private val _fromApi = MutableLiveData<Boolean>()
    val fromApi: LiveData<Boolean>
        get() = _fromApi

    /**
     * Constructor
     * Dependency injection.
     * */
    init {
        App.component.inject(this)
        _fromApi.value = false
    }

    /**
     * Changes [fromApi] to given [value]
     * @param value [Boolean]
     * */
    fun setFromApi(value: Boolean) {
        _fromApi.postValue(value)
    }

    /**
     * Update [SavedSerie] rating in database.
     * @param serie [SavedSerie]
     * */
    fun updateSerieRating(serie: SavedSerie) {
        doAsync {
            savedSerieRepository.updateSavedSerieRating(serie)
        }
    }

}