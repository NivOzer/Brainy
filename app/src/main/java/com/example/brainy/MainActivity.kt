package com.example.brainy

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.brainy.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navbar: BottomNavigationView = binding.navbar

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_brainy, R.id.navigation_settings
            )
        )

        // Add a destination change listener to hide/show BottomNavigationView
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.navigation_brainy -> navbar.visibility = View.GONE
                else -> navbar.visibility = View.VISIBLE
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navbar.setupWithNavController(navController)
        enableEdgeToEdge()
        supportActionBar?.hide()
    }

    override fun onBackPressed() {
        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Check if the current destination is HomeFragment
        if (navController.currentDestination?.id == R.id.navigation_home) {
            // Exit the app if the back button is pressed on HomeFragment
            finish()
        } else {
            // Otherwise, handle the back press to navigate to the previous fragment
            super.onBackPressed()
        }
    }

}