package com.example.watchlist.network

import com.example.watchlist.model.LoginData
import com.example.watchlist.model.LoginResource
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface TVDBApi {

    @Headers("Content-Type: application/json")
    @POST("login")
    fun login(@Body data: LoginData): Observable<LoginResource>

}