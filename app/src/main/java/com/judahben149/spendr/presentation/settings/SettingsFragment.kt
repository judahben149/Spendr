package com.judahben149.spendr.presentation.settings

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.ScreenPrefsBinding
import com.judahben149.spendr.utils.Constants.EXPORT_BUDGET_DIALOG
import com.judahben149.spendr.utils.Constants.SETTINGS_DIALOG
import com.judahben149.spendr.utils.SessionManager
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint
import soup.neumorphism.NeumorphCardView
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat() {


    val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var sessionManager: SessionManager

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val toolBarTitle = view.findViewById<TextView>(R.id.tv_toolbar_title_prefs)
        val backButton = view.findViewById<FrameLayout>(R.id.fl_back_btn_prefs)

        toolBarTitle.animateToolBarTitle()

//        backButton.setOnClickListener {
//            navController.navigateUp()
//        }

        val readSmsPreference = findPreference<Preference?>("READ_SMS")
        readSmsPreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, newValue ->
            val switched = newValue as? Boolean ?: false

            if (switched) {
                sessionManager.toggleSmsEntryFunctionality(true)
            } else {
                sessionManager.toggleSmsEntryFunctionality(false)
            }

            true
        }

        val deleteEntriesPreference = findPreference<Preference?>("deleteEntries")
        deleteEntriesPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener { _ ->
            SettingsDialogFragment.newInstance("entries").show(childFragmentManager, SETTINGS_DIALOG)
            true
        }

        val deleteRemindersPreference = findPreference<Preference?>("deleteReminders")
        deleteRemindersPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener { _ ->
            SettingsDialogFragment.newInstance("reminders").show(childFragmentManager, SETTINGS_DIALOG)
            true
        }

        val exportBudgetPreference = findPreference<Preference?>("exportBudget")
        exportBudgetPreference?.onPreferenceClickListener = Preference.OnPreferenceClickListener { _ ->
            ExportBudgetDialogFragment().show(childFragmentManager, EXPORT_BUDGET_DIALOG)
            true
        }

        val aboutPreferences = findPreference<Preference?>("about")
        aboutPreferences?.onPreferenceClickListener = Preference.OnPreferenceClickListener { _ ->
            val websiteUrl = "https://github.com/judahben149/Spendr"

            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
            startActivity(intent)
            true
        }
    }
}