package com.example.watchlist.model

import com.example.watchlist.utils.API_KEY
import com.example.watchlist.utils.USER_KEY
import com.example.watchlist.utils.USER_NAME

data class LoginData (val apikey: String = API_KEY,
                      val userkey: String = USER_KEY,
                      val username: String = USER_NAME) {
}