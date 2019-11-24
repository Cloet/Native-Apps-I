package com.example.watchlist.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.watchlist.App
import com.example.watchlist.base.BaseViewModel
import com.example.watchlist.model.LoginData
import com.example.watchlist.model.LoginResource
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.model.SerieResource
import com.example.watchlist.network.TVDBApi
import com.example.watchlist.utils.API_KEY
import com.example.watchlist.utils.API_URL
import com.example.watchlist.utils.USER_KEY
import com.example.watchlist.utils.USER_NAME
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    private val foundSeriesObject = MutableLiveData<List<SavedSerie>>()

    @Inject
    lateinit var tvDbApi: TVDBApi

    private var subscription: Disposable

    private var token: String = ""


    init {
        //subscription = tvDbApi.login(LoginData(API_KEY, USER_KEY, USER_NAME))
        //    .subscribeOn(Schedulers.io())
        //    //we like the fetched data to be displayed on the MainTread (UI)
        //    .observeOn(AndroidSchedulers.mainThread())
        //    .subscribe(
        //        { result -> onRetrieveTokenSucces(result) },
        //        { error -> onRetrieveTokenError(error) }
        //    )
        token = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NzUxOTU5OTEsImlkIjoiTW92aWVMaXN0Iiwib3JpZ19pYXQiOjE1NzQ1OTExOTEsInVzZXJpZCI6MjIzOTI3MCwidXNlcm5hbWUiOiJtYXRoaWFzY2xvZXQifQ.xkJciXJfeIJBnRhr8qdBaBlyMXl7OkZRZxQWRVbtGX6f4_9nHrPMoRiif_XlK1NUFthuPEbqQqbpHn25TvUJz77dpzAaPH2sbLHZr0pTJMlY48db-TtRfaxWYYGGmZon4PRAG8rh34GyQNAGQ0gxI27yoD-jqBBdOt6OS4C9Dl3zt0iOPMa-dveZr-Q-tJp4DleVZk8biMNWyi-U9oRB0k3c0_jJoqcVog4lSu43Orsx_LKsvgcxg6CH2jbYuucFcoRxA-YSteE5twE4VxQYLcAMPrRKKLrEj_jqtKoZV5yS5vgrTDAaqz3pospyNKsPCLDkPRkOb-AeoKMVufqldA"
        subscription = tvDbApi.searchSeries("starwars","Bearer " + token)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveSerieSucces(result) },
                { error -> onRetrieveSerieError(error)}
            )
    }

    private fun onRetrieveTokenError(error: Throwable) {
        Log.e("mainViewModel", error.message)
    }

    private fun onRetrieveTokenSucces(result: LoginResource) {
        token = result.token
    }

    private fun RetrieveSeries() {
        subscription = tvDbApi.searchSeries("starwars","Bearer" + token)
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

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    fun getSerieDataObject(): LiveData<List<SavedSerie>> {
        return foundSeriesObject
    }

}