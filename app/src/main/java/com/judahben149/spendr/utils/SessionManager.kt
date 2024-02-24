package com.judahben149.spendr.utils

import android.content.Context
import android.content.SharedPreferences
import com.judahben149.spendr.utils.Constants.KEY_BALANCE_HIDDEN_STATE
import javax.inject.Inject

class SessionManager @Inject constructor(private val sharedPrefs: SharedPreferences) {

    fun toggleBalanceVisibility(isBalanceHidden: Boolean) {
        sharedPrefs.saveToPreferences(KEY_BALANCE_HIDDEN_STATE, isBalanceHidden)
    }

    fun checkBalanceVisibility(): Boolean {
        val isHidden = sharedPrefs.getBoolean(KEY_BALANCE_HIDDEN_STATE, true)
        return isHidden
    }
}