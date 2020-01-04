package com.example.watchlist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.watchlist.R
import com.example.watchlist.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * Main Activity extends AppCompatActivity
 * */
class MainActivity : AppCompatActivity() {

    private lateinit var bottomNav : BottomNavigationView

    /**
     * Creates the Main Activity.
     * Initialize binding, navController and set up the bottom navigation bar for MainActivity.
     * @param savedInstanceState [Bundle]
     * */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        bottomNav = findViewById(R.id.bottom_navigation)

        bottomNav.setupWithNavController(Navigation.findNavController(this, R.id.nav_host_fragment))
        bottomNav.menu.getItem(1).isChecked = true

        val navController = this.findNavController(R.id.nav_host_fragment)
        NavigationUI.setupActionBarWithNavController(this,navController)

        bottomNav.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.mainFragment)
                    true
                }
                R.id.navigation_myList -> {
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.serieListFragment)
                    true
                }
                R.id.navigation_AddNew -> {
                    Navigation.findNavController(this , R.id.nav_host_fragment).navigate(R.id.addSerieListFragment)
                    true
                }
                else -> false
            }
        }
    }

    /**
     * This method is called whenever the user chooses to navigate Up within MainActivity's hierarchy from the action bar.
     * @return Boolean
     */
    override fun onSupportNavigateUp(): Boolean {
        val navController = this.findNavController(R.id.nav_host_fragment)
        return navController.navigateUp()
    }

}
