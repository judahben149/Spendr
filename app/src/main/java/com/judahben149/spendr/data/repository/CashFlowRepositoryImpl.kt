package com.judahben149.spendr.data.repository

import android.util.Log
import androidx.annotation.WorkerThread
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.data.paging.CashEntryPagingSource
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.INITIAL_LOAD_SIZE
import com.judahben149.spendr.utils.Constants.PAGE_SIZE
import com.judahben149.spendr.utils.Constants.PREFETCH_DISTANCE
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

class CashFlowRepositoryImpl @Inject constructor(
    private val cashFlowDao: CashFlowDao
) : CashFlowRepository {

    override suspend fun saveEntry(cashEntryEntity: CashEntryEntity) {
        cashFlowDao.saveEntry(cashEntryEntity)
    }

    override suspend fun saveNewCategory(categoryEntity: CategoryEntity) {
        cashFlowDao.saveNewCategory(categoryEntity)
    }

    override fun getCategories(): Flow<List<CategoryEntity>> {
        return cashFlowDao.getCategories()
    }

    override fun getALlCashEntries(): Flow<List<CashEntryEntity>> {
        return cashFlowDao.getAllCashEntries()
    }

    override fun getALlPagedCashEntries(): Flow<PagingData<CashEntryEntity>> {
        Log.d("TAGM", "getALlPagedCashEntries: repository")

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_SIZE,
                enablePlaceholders = false,
                initialLoadSize = INITIAL_LOAD_SIZE
            ),
            pagingSourceFactory = {
                CashEntryPagingSource(cashFlowDao)
            }
        ).flow
    }


    override suspend fun getEntryDetail(entryId: Int): CashEntryEntity {
       return cashFlowDao.getEntryDetail(entryId)
    }
}