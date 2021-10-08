package com.movieapp.utils

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import java.util.*

object LocaleHelper {


    fun onAttach(context: Context?): Context? {
        val lang: String? = getPersistedData(context, Locale.getDefault().language)
        return setLocale(context, lang)
    }

    fun onAttach(context: Context?, defaultLanguage: String): Context? {
        val lang: String? = getPersistedData(context, defaultLanguage)
        return setLocale(context, lang)
    }

    fun getLanguage(context: Context?): String? {
        return context?.let { getPersistedData(it, Locale.getDefault().language) }
    }

    fun setLocale(context: Context?, language: String?): Context? {
        persist(context, language)
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            updateResources(context, language)
        } else updateResourcesLegacy(context, language)
    }

    @JvmStatic
    fun getLocale(context: Context): String? {
        return PrefSettings.getLocale(context)
    }

    private fun getPersistedData(context: Context?, defaultLanguage: String): String? {
        return PrefSettings.getLocale(context)
    }

    private fun persist(context: Context?, language: String?) {
        PrefSettings.saveLocale(context, language)
    }

    @TargetApi(Build.VERSION_CODES.N)
    private fun updateResources(context: Context?, language: String?): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val configuration = context!!.resources.configuration
        configuration.setLocale(locale)
        configuration.setLayoutDirection(locale)
        return context.createConfigurationContext(configuration)
    }

    private fun updateResourcesLegacy(context: Context?, language: String?): Context? {
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context!!.resources
        val configuration = resources.configuration
        configuration.locale = locale
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            configuration.setLayoutDirection(locale)
        }
        resources.updateConfiguration(configuration, resources.displayMetrics)
        return context
    }
}
