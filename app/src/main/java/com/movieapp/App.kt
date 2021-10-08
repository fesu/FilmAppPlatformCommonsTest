package com.movieapp

import android.app.Application
import android.content.Context
import com.movieapp.utils.LocaleHelperNew
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class App : Application(){
    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(LocaleHelperNew.onAttach(base, "en"))
    }

    /*override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }


    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        PrefSettings.getLocale(this)?.let { LocaleHelper.setLocale(this, it) };
    }*/
}