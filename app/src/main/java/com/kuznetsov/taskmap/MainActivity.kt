package com.kuznetsov.taskmap

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.text.SimpleDateFormat
import java.util.Date

const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    lateinit var bottomNavView : BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dateInMillis = System.currentTimeMillis();
        val date = Date(dateInMillis)
        val sdf = SimpleDateFormat("dd.MM.yyyy")
        val dateInStr = sdf.format(date)
        Log.i(TAG, "dateInMillis = $dateInMillis")
        Log.i(TAG, "date = $date")
        Log.i(TAG, "dateInStr = $dateInStr")
        val zeroDate = Date(0)
        Log.i(TAG, "zeroDate = $zeroDate")


        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController

        bottomNavView = findViewById(R.id.bottom_nav)
        bottomNavView.setupWithNavController(navController)
    }
}