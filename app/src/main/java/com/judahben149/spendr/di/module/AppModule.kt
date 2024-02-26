package com.judahben149.spendr.di.module

import android.content.Context
import android.content.SharedPreferences
import com.judahben149.spendr.utils.PermissionHelper
import com.judahben149.spendr.utils.SessionManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun providesContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun providesSessionManager(
        sharedPreferences: SharedPreferences,
        @Named("defaultSharedPref") defaultSharedPreferences: SharedPreferences
    ): SessionManager {
        return SessionManager(sharedPreferences, defaultSharedPreferences)
    }

    @Provides
    @Singleton
    fun providesPermissionHelper(context: Context): PermissionHelper {
        return PermissionHelper(context)
    }
}