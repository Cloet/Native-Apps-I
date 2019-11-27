package com.example.watchlist.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.watchlist.R
import com.example.watchlist.fragments.MainFragment
import com.example.watchlist.fragments.SerieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.main_container, MainFragment())
                .commit()
        }

    }

    fun launchSerieListActivity(view: View) {
        val intent = Intent(this, SerieListActivity::class.java)
        startActivity(intent)
    }

    fun launchAddSerieListActivity(view: View) {
        val intent = Intent(this, AddSerieListActivity::class.java)
        startActivity(intent)
    }


}
