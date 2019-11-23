package com.example.watchlist.base

import android.net.Network
import androidx.lifecycle.ViewModel
import com.example.watchlist.injection.component.ViewModelInjectorComponent
import com.example.watchlist.injection.component.DaggerViewModelInjectorComponent
import com.example.watchlist.injection.module.NetworkModule
import com.example.watchlist.ui.MainViewModel

abstract class BaseViewModel : ViewModel() {

    private val injectorComponent: ViewModelInjectorComponent = DaggerViewModelInjectorComponent
        .builder()
        .networkModule(NetworkModule)
        .build()

    init {
        inject()
    }

    private fun inject() {
        when (this) {
            is MainViewModel -> injectorComponent.inject(this)
        }
    }

}