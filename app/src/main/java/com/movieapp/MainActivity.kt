package com.movieapp

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.databinding.ActivityMainBinding
import com.movieapp.utils.LocaleHelper
import com.movieapp.workmanager.MyWorker
import com.movieapp.utils.PrefSettings
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_settings
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        setTheme()
        setupWorkManager()
    }

    private fun setupWorkManager() {
//        val workRequest = OneTimeWorkRequest.Builder(MyWorker::class.java).build()

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(MyWorker::class.java, 30, TimeUnit.MINUTES)
                .build()
        //Enqueuing the work request
        WorkManager.getInstance(this).enqueue(periodicWorkRequest)

        //Listening to the work status
        WorkManager.getInstance(this).getWorkInfoByIdLiveData(periodicWorkRequest.id)
            .observe(this, {
                Log.d("Worker", it.state.name.trimIndent())
            })

    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelper.onAttach(newBase));
    }

    private fun setTheme() {
        if (this.let { PrefSettings.isDarkModeOn(it) }){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        }
        else{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }
}