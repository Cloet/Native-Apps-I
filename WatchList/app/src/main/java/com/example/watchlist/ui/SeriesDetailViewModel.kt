package com.example.watchlist.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.watchlist.App

class SeriesDetailViewModel: ViewModel() {

    private val _fromApi = MutableLiveData<Boolean>()
    val fromApi: LiveData<Boolean>
        get() = _fromApi

    init {
        App.component.inject(this)
        _fromApi.value = false
    }

    fun setFromApi(value: Boolean) {
        _fromApi.postValue(value)
    }

}