package com.movieapp.utils

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import java.util.*


object LocaleHelper {

    @JvmStatic
    fun setLocaleNew(context: Context, language: String) {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val config = Configuration()
        config.locale = locale
        context.resources.updateConfiguration(config,
                context.resources.displayMetrics)

        // Store language selection to Shared Preference.
        PrefSettings.saveLocale(context, language)
    }

    @JvmStatic
    fun getLocale(context: Context): String? {
        return PrefSettings.getLocale(context)
    }

    fun setLocale(context: Context, languageCode: String) {
        val config = context.resources.configuration
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            config.setLocale(locale)
        else
            config.locale = locale

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            context.createConfigurationContext(config)
        context.resources.updateConfiguration(config, context.resources.displayMetrics)

        // Store language selection to Shared Preference.
        PrefSettings.saveLocale(context, languageCode)
    }
}
