package com.movieapp

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.movieapp.databinding.ActivityMainBinding
import com.movieapp.utils.LocaleHelperNew
import com.movieapp.utils.PrefSettings
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

       /* val config = resources.configuration
        val lang = "fr" // your language code
        val locale = Locale(lang)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)*/

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
//        setLanguage()
    }

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(LocaleHelperNew.onAttach(newBase));
    }

    private fun setLanguage() {
        try {
            var selectedLanguage: String? = null
            // Get previously Selected language by User
            selectedLanguage = LocaleHelperNew.getLocale(this)
            if (selectedLanguage != null) {
                if (selectedLanguage == "en") {
                    LocaleHelperNew.setLocale(this, "en")
                }
                else{
                    LocaleHelperNew.setLocale(this, "fr")
                }
            }
        }
        catch (e:Exception){
            e.printStackTrace()
        }
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