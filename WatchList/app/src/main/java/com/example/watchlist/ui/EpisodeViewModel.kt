package com.example.watchlist.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.*
import com.example.watchlist.network.TVDBApi
import com.example.watchlist.persistence.SavedSerieRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class EpisodeViewModel: ViewModel() {

    /**
     * Properties
     * */
    @Inject
    lateinit var tvDbApi: TVDBApi

    @Inject
    lateinit var savedSerieRepository: SavedSerieRepository

    private var subscription: Disposable? = null

    var foundEpisodeObject = MutableLiveData<List<Episode>>()

    var foundActorObject = MutableLiveData<List<Actor>>()

    /**
     * Constructor
     * Dagger dependency injection.
     * */
    init {
        App.component.inject(this)
    }

    /**
     * Retrieve all [Episode] with given [id] from [tvDbApi]
     * @param id of a [SavedSerie]
     * */
    fun RetrieveEpisodes(id: String?) {
        val prefs = Preferences(App.application)

        val token = prefs.Token

        if (id.isNullOrEmpty() || token.isNullOrEmpty()) return

        subscription

        subscription = tvDbApi.searchEpisodes(id, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveEpisodeSucces(result) },
                { error -> onRetrieveEpisodeError(error)}
            )
    }

    /**
     * Retrieve all [Actor] with given [id] from [tvDbApi]
     * @param id of a [SavedSerie]
     * */
    fun RetrieveActors(id: String?) {
        val prefs = Preferences(App.application)

        val token = prefs.Token

        if (id.isNullOrEmpty() || token.isNullOrEmpty()) return

        subscription

        subscription = tvDbApi.searchActors(id, token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveActorSucces(result) },
                { error -> onRetrieveActorError(error)}
            )
    }

    private fun onRetrieveActorError(error: Throwable) {
        Log.e("episodeViewModel", error.message)
    }

    private fun onRetrieveActorSucces(result: ActorResource) {
        foundActorObject.value = result.actors
    }

    private fun onRetrieveEpisodeError(error: Throwable) {
        Log.e("episdoeViewModel", error.message)
    }

    private fun onRetrieveEpisodeSucces(result: EpisodeResource) {
        foundEpisodeObject.value = result.episodes
    }

    override fun onCleared() {
        super.onCleared()
        subscription?.dispose()
    }




}