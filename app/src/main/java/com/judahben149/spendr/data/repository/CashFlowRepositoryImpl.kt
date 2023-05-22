package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import javax.inject.Inject

class CashFlowRepositoryImpl @Inject constructor(private val cashFlowDao: CashFlowDao): CashFlowRepository {

    override suspend fun saveEntry(cashEntryEntity: CashEntryEntity) {
        cashFlowDao.saveEntry(cashEntryEntity)
    }

    override suspend fun saveNewCategory(categoryEntity: CategoryEntity) {
        cashFlowDao.saveNewCategory(categoryEntity)
    }
}