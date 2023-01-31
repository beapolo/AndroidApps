package com.example.showtracker.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.*
import androidx.navigation.ui.*
import com.example.showtracker.R
import com.example.showtracker.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNavigationAppBar()
    }

    private fun setNavigationAppBar() {
        with(binding) {
            setSupportActionBar(topAppBar)

            val navHostFragment =
                supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
            navController = navHostFragment.findNavController()

            appBarConfiguration = AppBarConfiguration(
                setOf(
                    R.id.show_list, R.id.favorite_shows, R.id.show_details, R.id.season_details
                )
            )
            setupActionBarWithNavController(navController, appBarConfiguration)
            bottomNavigation.setupWithNavController(navController)
        }
    }
}