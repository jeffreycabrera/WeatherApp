package com.example.weatherapp.ui

import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupActionBarWithNavController
import com.example.weatherapp.R
import com.example.weatherapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    private lateinit var binding: ActivityMainBinding

    private lateinit var navController: NavController

    private lateinit var navHostFragment: NavHostFragment

    // TODO: low: Property "sharedPreferences" is never used
    private lateinit var sharedPreferences: SharedPreferences

    private val navGraphId = R.navigation.navigation_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        toolbar = binding.appToolbar
        setSupportActionBar(toolbar)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.mainNavHostFragment) as NavHostFragment
        navController = navHostFragment.navController.apply {
            setGraph(navGraphId)
            setupActionBarWithNavController(this)
            addOnDestinationChangedListener(ToolbarOnDestinationChangedListener(toolbar))
        }
    }
}
