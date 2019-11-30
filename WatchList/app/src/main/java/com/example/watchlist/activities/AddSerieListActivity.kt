package com.example.watchlist.activities

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import com.example.watchlist.R
import com.example.watchlist.fragments.AddSerieListFragment
import com.example.watchlist.fragments.SerieDetailFragment
import com.example.watchlist.model.SavedSerie

class AddSerieListActivity: AppCompatActivity(), SerieDetailFragment.onSerieSelected{

    override fun onSerieSelected(serie: SavedSerie) {
        val detailsFragment = SerieDetailFragment.newInstance(serie)
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_add_series_container, detailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_series)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayShowTitleEnabled(false)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_add_series_container, AddSerieListFragment())
                .commit()
        }

    }



}