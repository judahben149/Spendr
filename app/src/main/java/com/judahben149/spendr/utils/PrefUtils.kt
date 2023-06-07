package com.judahben149.spendr.utils

import android.content.SharedPreferences

fun SharedPreferences.saveToPreferences(key: String, value: String) {
        val editor = this.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun SharedPreferences.saveToPreferences(key: String, value: Int) {
        val editor = this.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun SharedPreferences.saveToPreferences(key: String, value: Long) {
        val editor = this.edit()
        editor.putLong(key, value)
        editor.apply()
    }

    fun SharedPreferences.saveToPreferences(key: String, value: Boolean) {
        val editor = this.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun SharedPreferences.getPrefString(key: String, defaultValue: String): String {
        return this.getString(key, defaultValue) ?: defaultValue
    }

    fun SharedPreferences.getPrefInt(key: String, defaultValue: Int): Int {
        return this.getInt(key, defaultValue)
    }

    fun SharedPreferences.getPrefLong(key: String, defaultValue: Long): Long {
        return this.getLong(key, defaultValue)
    }

    fun SharedPreferences.getPrefBoolean(key: String, defaultValue: Boolean): Boolean {
        return this.getBoolean(key, defaultValue)
    }