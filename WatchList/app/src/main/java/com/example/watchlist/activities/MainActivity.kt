package com.example.watchlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.watchlist.R
import com.example.watchlist.fragments.SerieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, SerieListFragment())
                .commit()
        // }

    }
}
