package com.example.watchlist.ui

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.watchlist.base.BaseViewModel
import com.example.watchlist.model.LoginData
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.network.TVDBApi
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel : BaseViewModel() {

    private val SerieObject = MutableLiveData<String>()

    @Inject
    lateinit var tvDbApi: TVDBApi

    private var subscription: Disposable

    init {
        subscription = tvDbApi.login(LoginData())
            .subscribeOn(Schedulers.io())
            //we like the fetched data to be displayed on the MainTread (UI)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { result -> onRetrieveQuoteSucces(result.token) },
                { error -> onRetrieveQuoteError(error) }
            )
    }

    private fun onRetrieveQuoteError(error: Throwable) {
        Log.e("quoteviewmodel", error.message)
    }

    private fun onRetrieveQuoteSucces(result: String) {
        SerieObject.value = result
        Log.e(result,result)
    }

}