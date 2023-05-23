package com.judahben149.spendr.di.module

import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.data.repository.DatabaseRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun providesCashFlowRepository(cashFlowDao: CashFlowDao): CashFlowRepositoryImpl {
        return CashFlowRepositoryImpl(cashFlowDao)
    }

    @Provides
    @Singleton
    fun providesDatabaseRepository(cashFlowDao: CashFlowDao): DatabaseRepositoryImpl {
        return DatabaseRepositoryImpl(cashFlowDao)
    }
}