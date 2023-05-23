package com.judahben149.spendr.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class CashFlowRepositoryImpl @Inject constructor(private val cashFlowDao: CashFlowDao): CashFlowRepository {

    override suspend fun saveEntry(cashEntryEntity: CashEntryEntity) {
        cashFlowDao.saveEntry(cashEntryEntity)
    }

    override suspend fun saveNewCategory(categoryEntity: CategoryEntity) {
        cashFlowDao.saveNewCategory(categoryEntity)
    }

    override fun getCategories(): Flow<List<CategoryEntity>> {
        CoroutineScope(Dispatchers.IO).launch {
            cashFlowDao.getCategories().collect { category ->
                Log.d("TAGM", "Category: $category")
            }
        }

        return cashFlowDao.getCategories()
    }
}