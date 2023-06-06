package com.judahben149.spendr.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceFragmentCompat
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.ScreenPrefsBinding
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import soup.neumorphism.NeumorphCardView

class SettingsFragment: PreferenceFragmentCompat() {


    val navController by lazy {
        findNavController()
    }

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
    }
}