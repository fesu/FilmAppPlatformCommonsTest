package com.movieapp.ui.settings

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.movieapp.R
import com.movieapp.databinding.FragmentSettingsBinding
import com.movieapp.utils.LocaleHelper
import com.movieapp.utils.PrefSettings
import dagger.hilt.android.AndroidEntryPoint

/**
 * Setting screen to manage Dark mode & language preferences.
 *
 */
@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var settingsViewModel: SettingsViewModel
    private var _binding: FragmentSettingsBinding? = null
    private var isLanguageSet:Boolean = false

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        settingsViewModel =
            ViewModelProvider(this).get(SettingsViewModel::class.java)

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setUIControls()

        return root
    }

    private fun setUIControls() {
        try {

            binding.switchTheme.isChecked = context?.let { PrefSettings.isDarkModeOn(it) } == true

            binding.switchTheme.setOnCheckedChangeListener { _, isChecked ->
                if (isChecked){
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                }
                else{
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }

                context?.let { PrefSettings.saveDarkModeStatus(it, isChecked) }

            }

            setLanguageOptions()
        }
        catch (e:Exception){
            e.printStackTrace()
        }

    }

    private fun setLanguageOptions() {

        // Set Language options in Spinner
        val arrayAdapter: ArrayAdapter<*>
        val list = arrayOf(
            "English",
            "French"
        )
        arrayAdapter = context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_list_item_1, list
            )
        }!!
        binding.spnrLanguage.adapter = arrayAdapter

        var selectedLanguage: String? = null
        // Get previously Selected language by User
        selectedLanguage = LocaleHelper.getLocale(requireContext())

        if (selectedLanguage != null) {
            if (selectedLanguage == "en") {
                binding.spnrLanguage.setSelection(0)

            } else {
                binding.spnrLanguage.setSelection(1)
            }
        }
        else{
            binding.spnrLanguage.setSelection(0)
        }

        binding.spnrLanguage.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                if (!isLanguageSet){
                    isLanguageSet = true
                    return
                }

                if (position == 0){
                    LocaleHelper.setLocale(context!!, "en")
                }
                else{
                    LocaleHelper.setLocale(context!!, "fr")
                }

                showRestartAppAlert()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

    }

    private fun showRestartAppAlert() {
        try {
            val builder = context?.let { AlertDialog.Builder(it) }
            //set title for alert dialog
            builder!!.setTitle(R.string.restart_dialog_title)
            //set message for alert dialog
            builder.setMessage(R.string.restart_dialog_message)
            builder.setIcon(android.R.drawable.ic_dialog_alert)

            //performing positive action
            builder.setPositiveButton(getString(R.string.title_restart)){ _, _ ->
                restartApp()
            }
            // Create the AlertDialog
            val alertDialog: AlertDialog = builder.create()
            // Set other dialog properties
            alertDialog.setCancelable(false)
            alertDialog.show()
        }
        catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun restartApp() {
        val packageManager: PackageManager = requireContext().packageManager
        val intent = packageManager.getLaunchIntentForPackage(requireContext().packageName)
        val componentName = intent!!.component
        val mainIntent = Intent.makeRestartActivityTask(componentName)
        requireContext().startActivity(mainIntent)
        Runtime.getRuntime().exit(0)
    }
}