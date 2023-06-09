package com.judahben149.spendr

import android.app.Application
import android.content.SharedPreferences
import android.widget.Toast
import com.judahben149.spendr.data.repository.DatabaseRepositoryImpl
import com.judahben149.spendr.utils.Constants.IS_FIRST_LAUNCH
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class MainApp: Application() {

    @Inject
    lateinit var databaseRepositoryImpl: DatabaseRepositoryImpl

    @Inject
    lateinit var appPrefs: SharedPreferences

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        preloadCategoryDatabaseOnFirstLaunch()

    }

    private fun preloadCategoryDatabaseOnFirstLaunch() {
        val isFirstLaunch = appPrefs.getBoolean(IS_FIRST_LAUNCH, true)

        if (isFirstLaunch) {
            CoroutineScope(Dispatchers.IO).launch {
                databaseRepositoryImpl.prefillDefaultCategories(applicationContext)
            }
        } else {
            val editor = appPrefs.edit()
            editor?.putBoolean(IS_FIRST_LAUNCH, false)?.apply()
        }
    }
}