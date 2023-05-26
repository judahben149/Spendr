package com.judahben149.spendr.data.repository

import androidx.paging.PagingData
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CashFlowRepository {

    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)

    suspend fun saveNewCategory(categoryEntity: CategoryEntity)

    fun getCategories(): Flow<List<CategoryEntity>>

    fun getALlCashEntries(): Flow<List<CashEntryEntity>>

    fun getALlPagedCashEntries(): Flow<PagingData<CashEntryEntity>>

    suspend fun getEntryDetail(entryId: Int): CashEntryEntity
}