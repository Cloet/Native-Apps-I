package com.example.watchlist.model

import android.content.Context
import android.content.SharedPreferences
import android.media.session.MediaSession

class Preferences (context: Context) {

    private val PREFS_FILENAME = "com.example.watchlist.token"
    private val JWT_TOKEN = "Token"

    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var Token: String
        get() = "Bearer " + prefs!!.getString(JWT_TOKEN, "")
        set(value) = prefs.edit().putString(JWT_TOKEN, value).apply()


}