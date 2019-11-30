package com.example.watchlist.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App
import com.example.watchlist.model.*
import com.example.watchlist.network.TVDBApi
import com.example.watchlist.utils.API_KEY
import com.example.watchlist.utils.USER_KEY
import com.example.watchlist.utils.USER_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.doAsync
import javax.inject.Inject

class MainViewModel : ViewModel() {

    private val foundSeriesObject = MutableLiveData<List<SavedSerie>>()

    @Inject
    lateinit var tvDbApi: TVDBApi

    private var subscription: Disposable

    val prefs : Preferences

    init {
        App.component.inject(this)

        prefs = Preferences(App.application)

        subscription = tvDbApi.login(LoginData(API_KEY, USER_KEY, USER_NAME))
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveTokenSucces(result) },
                { error -> onRetrieveTokenError(error) }
            )
    }

    private fun onRetrieveTokenError(error: Throwable) {
        Log.e("mainViewModel", error.message)
    }

    private fun onRetrieveTokenSucces(result: LoginResource) {
        prefs.Token = result.token
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun getSerieDataObject(): LiveData<List<SavedSerie>> {
        return foundSeriesObject
    }

}