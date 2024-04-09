package com.judahben149.spendr.di.module

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import com.judahben149.spendr.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SharedPrefsModule {

    @Provides
    @Singleton
    fun providesSharedPrefModule(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(Constants.SETTINGS_FILE, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    @Named("defaultSharedPref")
    fun providesDefaultSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }
}