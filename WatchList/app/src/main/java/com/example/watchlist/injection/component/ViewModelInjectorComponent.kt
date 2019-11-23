package com.example.watchlist.injection.component

import android.net.Network
import com.example.watchlist.injection.module.NetworkModule
import com.example.watchlist.ui.MainViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules= [NetworkModule::class])
interface ViewModelInjectorComponent {

    fun inject(mainViewModel: MainViewModel)

    @Component.Builder
    interface  Builder {
        fun build(): ViewModelInjectorComponent

        fun networkModule(networkModule: NetworkModule): Builder
    }

}