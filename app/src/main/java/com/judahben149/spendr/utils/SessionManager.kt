package com.judahben149.spendr.utils

import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.judahben149.spendr.utils.Constants.KEY_BALANCE_HIDDEN_STATE
import com.judahben149.spendr.utils.Constants.RECEIVE_SMS_ENTRIES
import javax.inject.Inject
import javax.inject.Named

class SessionManager @Inject constructor(
    private val sharedPrefs: SharedPreferences,
    @Named("defaultSharedPref") private val defaultSharedPrefs: SharedPreferences
) {

    fun toggleBalanceVisibility(isBalanceHidden: Boolean) {
        sharedPrefs.saveToPreferences(KEY_BALANCE_HIDDEN_STATE, isBalanceHidden)
    }

    fun checkBalanceVisibility(): Boolean {
        val isHidden = sharedPrefs.getBoolean(KEY_BALANCE_HIDDEN_STATE, true)
        return isHidden
    }

    fun toggleSmsEntryFunctionality(canReceive: Boolean) {
        sharedPrefs.saveToPreferences(RECEIVE_SMS_ENTRIES, canReceive)
    }

    fun canReceiveSmsEntries(): Boolean {
        return sharedPrefs.getBoolean(RECEIVE_SMS_ENTRIES, false)
    }

    fun getUserName(): String {
        return defaultSharedPrefs.getString(Constants.USER_NAME, "there") ?: "there"
    }

    fun updateUserName(userName: String) {
        defaultSharedPrefs.edit().putString(Constants.USER_NAME, userName).apply()
    }

    fun isFirstLaunch(): Boolean {
        return sharedPrefs.getBoolean(Constants.IS_FIRST_LAUNCH, true)
    }

    fun updateIsFirstLaunch(isFirstLaunch: Boolean) {
        sharedPrefs.edit().putBoolean(Constants.IS_FIRST_LAUNCH, isFirstLaunch).apply()
    }
}