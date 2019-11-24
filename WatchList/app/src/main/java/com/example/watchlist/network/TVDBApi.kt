package com.example.watchlist.network

import com.example.watchlist.model.LoginData
import com.example.watchlist.model.LoginResource
import com.example.watchlist.model.SavedSerie
import com.example.watchlist.model.SerieResource
import io.reactivex.Observable
import retrofit2.http.*

interface TVDBApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body data: LoginData): Observable<LoginResource>

    @Headers("Content-Type: application/json")
    @GET("search/series")
    fun searchSeries(@Query("name") serieName: String, @Header("Authorization") authToken: String): Observable<SerieResource>

}