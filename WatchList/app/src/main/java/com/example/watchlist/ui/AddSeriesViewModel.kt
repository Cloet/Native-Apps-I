package com.example.watchlist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.Preferences
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.model.SerieResource
import com.example.watchlist.network.TVDBApi
import com.example.watchlist.persistence.SavedSerieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class AddSeriesViewModel: ViewModel() {

    /**
     * Properties
     * */
    @Inject
    lateinit var tvDbApi: TVDBApi

    private var subscription: Disposable? = null

    @Inject
    lateinit var savedSeriesRepository: SavedSerieRepository

    var foundSeriesObject = MutableLiveData<List<SavedSerie>>()

    private val _retrieving = MutableLiveData<Boolean>()
    val retrieving: LiveData<Boolean>
        get() = _retrieving

    /**
     * Constructor.
     * Dagger dependency injection.
     * */
    init {
        App.component.inject(this)
    }

    /**
     * Retrieves all [SavedSerie] with a given [name] from [tvDbApi]
     * @param name of a [SavedSerie]
     * */
    fun RetrieveSeries(name: String?) {
        this._retrieving.value = true

        val prefs = Preferences(App.application)

        val token = prefs.Token

        if (name.isNullOrEmpty() || token.isNullOrEmpty()) return

        subscription = tvDbApi.searchSeries(name, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveSerieSucces(result) },
                { error -> onRetrieveSerieError(error)}
            )
    }

    private fun onRetrieveSerieError(error: Throwable) {
        Log.e("mainViewModel", error.message)
    }

    private fun onRetrieveSerieSucces(result: SerieResource) {
        this._retrieving.value = false
        foundSeriesObject.value = result.series
    }

    /**
     * Insert a [SavedSerie] into the room database.
     * @param serie [SavedSerie]
     * */
    fun insertSerie(serie: SavedSerie) {
        doAsync {
            savedSeriesRepository.insert(serie)
        }
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }

}