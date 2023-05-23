package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

interface CashFlowRepository {

    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)

    suspend fun saveNewCategory(categoryEntity: CategoryEntity)

    fun getCategories(): Flow<List<CategoryEntity>>
}