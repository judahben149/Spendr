package com.judahben149.spendr.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.utils.Constants
import javax.inject.Inject

class CashEntryPagingSource @Inject constructor(private val cashFlowDao: CashFlowDao): PagingSource<Int, CashEntryEntity>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CashEntryEntity> {
        val page: Int = params.key ?: Constants.INITIAL_PAGE

        return try {
            Log.d("TAGM", "getALlPagedCashEntries: paging source")
            val entities = cashFlowDao.getAllPagedCashEntries(params.loadSize, page * params.loadSize)

            LoadResult.Page(
                data = entities,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entities.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, CashEntryEntity>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}