package com.judahben149.spendr.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.judahben149.spendr.data.local.AppDatabase
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.utils.Constants.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun providesAppDatabase(@ApplicationContext context: Context): RoomDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            APP_DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun providesCashFlowDao(appDatabase: AppDatabase): CashFlowDao {
        return appDatabase.CashFlowDao()
    }
}