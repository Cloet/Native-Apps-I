package com.example.watchlist.ui

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.LoginData
import com.example.watchlist.model.Preferences
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.model.SerieResource
import com.example.watchlist.network.TVDBApi
import com.example.watchlist.persistence.SavedSerieRepository
import com.example.watchlist.utils.API_KEY
import com.example.watchlist.utils.USER_KEY
import com.example.watchlist.utils.USER_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class AddSeriesViewModel: ViewModel() {

    @Inject
    lateinit var tvDbApi: TVDBApi

    private var subscription: Disposable? = null

    @Inject
    lateinit var savedSeriesRepository: SavedSerieRepository

    var foundSeriesObject = MutableLiveData<List<SavedSerie>>()


    init {
        App.component.inject(this)
    }

    fun RetrieveSeries(name: String?) {
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
        foundSeriesObject.value = result.series
    }

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