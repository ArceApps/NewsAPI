package com.arceapps.newsapi.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.arceapps.newsapi.R
import com.arceapps.newsapi.databinding.ActivityMainBinding
import com.arceapps.newsapi.extensions.loadImageId
import com.arceapps.newsapi.utils.UtilMethods
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        navController = navHostFragment.navController

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_bookmarks,
                R.id.navigation_dashboard,
                R.id.navigation_search
            )
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        binding.dialogNoInternet.noInternetImage.loadImageId(R.drawable.no_internet)

        binding.dialogNoInternet.buttonTryAgain.setOnClickListener {
            getInternetState()
        }

        getInternetState()

        navView.setOnItemSelectedListener { item ->
            if (item.itemId == R.id.navigation_bookmarks) {
                navController.navigate(R.id.navigation_bookmarks)
            }
            if (item.itemId == R.id.navigation_dashboard) {
                navController.navigate(R.id.navigation_dashboard)
            }
            if (item.itemId == R.id.navigation_search) {
                navController.navigate(R.id.navigation_search)
            }
            if (item.itemId == R.id.navigation_home) {
                navController.navigate(R.id.navigation_home)
            }
            true
        }
    }

    override fun onStart() {
        super.onStart()
        getInternetState()
    }

    override fun onResume() {
        getInternetState()
        super.onResume()
    }

    override fun onBackPressed() {
        if (binding.root.visibility == View.VISIBLE) {
            val snackbar =
                Snackbar.make(binding.root, "Are you sure you want to exit?", Snackbar.LENGTH_LONG)
                    .setAction("Yes") {
                        finishAffinity()
                    }
                    .setActionTextColor(Color.RED)
            snackbar.show()
        } else
            finish()
    }

    private fun getInternetState() {
        if (UtilMethods.isInternetAvailable(applicationContext)) {
            binding.mainLayout.visibility = View.VISIBLE
            binding.dialogNoInternet.root.visibility = View.GONE
        } else {
            binding.mainLayout.visibility = View.GONE
            binding.dialogNoInternet.root.visibility = View.VISIBLE
            Snackbar.make(binding.root, R.string.no_internet_connection, Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}