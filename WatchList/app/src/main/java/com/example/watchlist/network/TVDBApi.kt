package com.example.watchlist.network

import com.example.watchlist.model.*
import io.reactivex.Observable
import retrofit2.http.*

interface TVDBApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body data: LoginData): Observable<LoginResource>

    @Headers("Content-Type: application/json")
    @GET("search/series")
    fun searchSeries(@Query("name") serieName: String, @Header("Authorization") authToken: String): Observable<SerieResource>

    @Headers("Content-Type: application/json")
    @GET("series/{id}/episodes")
    fun searchEpisodes(@Path("id") seriesId: String, @Header("Authorization") authToken: String): Observable<EpisodeResource>

    @Headers("Content-Type: application/json")
    @GET("series/{id}/actors")
    fun searchActors(@Path("id") seriesId: String, @Header("Authorization") authToken: String): Observable<ActorResource>
}