package com.movieapp.utils

import android.content.Context

/**
 * This is Shared Preference Manager class to maintain local storage
 */
class PrefSettings {

    companion object {
        private const val movieAppPrefName = "MOVIE_PREF"

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

    }


}