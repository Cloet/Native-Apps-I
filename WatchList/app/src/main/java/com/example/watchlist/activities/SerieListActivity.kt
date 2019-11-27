package com.example.watchlist.activities

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.watchlist.R
import com.example.watchlist.fragments.AddSerieListFragment
import com.example.watchlist.fragments.MainFragment
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.ui.SavedSeriesViewModel
import javax.inject.Inject

class SerieListActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_saved_series)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_series_container, SerieListFragment())
                .commit()
        }


    }

}