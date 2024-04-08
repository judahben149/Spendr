package com.judahben149.spendr.data.repository

import androidx.paging.PagingData
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.domain.model.EntryListData
import kotlinx.coroutines.flow.Flow

interface CashFlowRepository {

    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)

    suspend fun saveNewCategory(categoryEntity: CategoryEntity)

    fun getCategories(): Flow<List<CategoryEntity>>

    fun getALlCashEntries(): Flow<List<CashEntryEntity>>

    fun getAllPagedIncome(): Flow<PagingData<EntryListData>>

    fun getAllPagedExpenditure(): Flow<PagingData<EntryListData>>

    suspend fun getEntryDetail(entryId: Int): CashEntryEntity

    suspend fun deleteEntry(entryId: Int)

    suspend fun deleteAllEntries()

    suspend fun deleteAllReminders()

    suspend fun deleteExpiredReminders(currentDate: Long)
}