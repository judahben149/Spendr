package com.judahben149.spendr.presentation

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.judahben149.spendr.R

class SettingsFragment: PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }
}