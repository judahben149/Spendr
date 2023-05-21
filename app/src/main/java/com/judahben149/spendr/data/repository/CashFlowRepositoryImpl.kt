package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import javax.inject.Inject

class CashFlowRepositoryImpl @Inject constructor(private val cashFlowDao: CashFlowDao): CashFlowRepository {

    override suspend fun saveEntry(cashEntryEntity: CashEntryEntity) {
        cashFlowDao.saveEntry(cashEntryEntity)
    }
}