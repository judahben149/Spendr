package com.judahben149.spendr.presentation.settings

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreference
import com.google.android.material.appbar.MaterialToolbar
import com.judahben149.spendr.R
import com.judahben149.spendr.presentation.MainActivity
import com.judahben149.spendr.presentation.SMS_REQUEST_CODE
import com.judahben149.spendr.presentation.components.ReusableCustomDialog
import com.judahben149.spendr.presentation.components.ReusableCustomDialogCallBack
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.EXPORT_BUDGET_DIALOG
import com.judahben149.spendr.utils.Constants.SETTINGS_DIALOG
import com.judahben149.spendr.utils.PermissionHelper
import com.judahben149.spendr.utils.SessionManager
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import com.judahben149.spendr.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment: PreferenceFragmentCompat(), PermissionResultCallback,
    ReusableCustomDialogCallBack {


    val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var sessionManager: SessionManager

    @Inject
    lateinit var permissionHelper: PermissionHelper

    private val viewModel: SettingsViewModel by viewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rootView = ((listView.parent as FrameLayout).parent as LinearLayout)
        val toolbar = LayoutInflater.from(requireContext()).inflate(R.layout.layout_preference_toolbar, rootView, false) as MaterialToolbar
        rootView.addView(toolbar, 0)

        val toolBarTitle = toolbar.findViewById<TextView>(R.id.tv_toolbar_title_prefs)
        val backButton = toolbar.findViewById<FrameLayout>(R.id.fl_back_btn_prefs)

        toolBarTitle.animateToolBarTitle()
        backButton.setOnClickListener {
            navController.navigateUp()
        }

        val readSmsPreference = findPreference<Preference?>("READ_SMS")

        readSmsPreference?.onPreferenceChangeListener = Preference.OnPreferenceChangeListener { pref, newValue ->
            val switched = newValue as? Boolean ?: false

            if (switched) {
                permissionHelper.requestNeededPermissions(requireActivity() as MainActivity, this)
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

    override fun onResume() {
        super.onResume()
        val readSmsPrefSwitch = findPreference<SwitchPreference?>("READ_SMS")

        if (!permissionHelper.isSmsPermissionGranted()) {
            readSmsPrefSwitch?.isChecked = false
            sessionManager.toggleSmsEntryFunctionality(false)
        } else {
            readSmsPrefSwitch?.isChecked = true
        }
    }

    private fun showNewPermissionInfoDialog() {
        val deleteDialog = ReusableCustomDialog.newInstance(
                this@SettingsFragment,
        "Grant SMS Permission",
        "SMS Permission is required to create entries from alerts",
        "Grant",
        "Ignore"
        )

        deleteDialog.show(childFragmentManager, Constants.GRANT_SMS_PERMISSION_DIALOG)
    }

    override fun onSmsPermissionGranted() {
        showToast(requireContext(), "Sms Permission Granted")
        sessionManager.toggleSmsEntryFunctionality(true)
    }

    override fun onSmsPermissionDenied() {
        showToast(requireContext(), "Sms Permission NOT Granted")
        showNewPermissionInfoDialog()
        sessionManager.toggleSmsEntryFunctionality(false)
    }

    private fun openSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        intent.data = Uri.parse("package:" + requireContext().packageName)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        startActivity(intent)
    }

    override fun onPositiveAction() { openSettings() }

    override fun onNegativeAction() {
        sessionManager.toggleSmsEntryFunctionality(false)
    }
}

interface PermissionResultCallback {
    fun onSmsPermissionGranted()

    fun onSmsPermissionDenied()
}