package com.example.watchlist.activities

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.watchlist.R
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.fragments.SerieListFragment
import com.example.watchlist.model.SavedSerie

class SerieListActivity: AppCompatActivity(), SerieDetailFragment.onSerieSelected {

    override fun onSerieSelected(serie: SavedSerie) {
        val detailsFragment = SerieDetailFragment.newInstance(serie)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_series_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_saved_series)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_series_container, SerieListFragment())
                .commit()
        }


    }

}