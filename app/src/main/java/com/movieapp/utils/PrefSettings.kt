package com.movieapp.utils

import android.content.Context

/**
 * This is Shared Preference Manager class to maintain local storage
 */
class PrefSettings {

    companion object {
        private const val movieAppPrefName = "MOVIE_PREF"
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"

        /**
         *  Save Dark Mode Status
         *
         * @param context
         * @param isDarkModeOn
         */
        @JvmStatic fun saveDarkModeStatus(context: Context, isDarkModeOn: Boolean) {
            val sharedPref =
                context.getSharedPreferences(movieAppPrefName, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putBoolean("isDarkModeOn", isDarkModeOn)
            editor.apply()
        }

        /**
         * Get Dark Mode Status
         *
         * @param context
         * @return
         */
        @JvmStatic fun isDarkModeOn(context: Context): Boolean {
            val sharedPref =
                context.getSharedPreferences(movieAppPrefName, Context.MODE_PRIVATE)
            return sharedPref.getBoolean("isDarkModeOn", false)
        }


        /**
         *  Save Locale to shared preference
         *
         * @param context
         * @param locale
         */
        @JvmStatic fun saveLocale(context: Context?, locale: String?) {
            val sharedPref =
                context!!.getSharedPreferences(movieAppPrefName, Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.putString(SELECTED_LANGUAGE, locale)
            editor.apply()
        }

        /**
         * Get Locale from shared preference
         *
         * @param context
         * @return
         */
        @JvmStatic fun getLocale(context: Context?): String? {
            val sharedPref =
                context!!.getSharedPreferences(movieAppPrefName, Context.MODE_PRIVATE)
            return sharedPref.getString(SELECTED_LANGUAGE, "en")
        }

    }


}