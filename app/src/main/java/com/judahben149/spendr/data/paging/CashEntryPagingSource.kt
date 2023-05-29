package com.judahben149.spendr.data.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.judahben149.spendr.data.local.CashFlowDao
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.domain.model.EntryListData
import com.judahben149.spendr.domain.model.MonthHeader
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.DateUtils.getMonthFromDateInMillis
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import javax.inject.Inject

class CashEntryPagingSource @Inject constructor(private val cashFlowDao: CashFlowDao, private val isIncomeEntry: Boolean) :
    PagingSource<Int, EntryListData>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EntryListData> {
        val page: Int = params.key ?: Constants.INITIAL_PAGE

        return try {

            val entries = if (isIncomeEntry) {
                cashFlowDao.getAllPagedIncome(params.loadSize, page * params.loadSize)
            } else {
                cashFlowDao.getAllPagedExpenditure(params.loadSize, page * params.loadSize)
            }

            val groupedEntries = entries.groupBy { entry ->
                getMonthFromDateInMillis(entry.transactionDate)
            }

            val combinedList = mutableListOf<EntryListData>()

            groupedEntries.forEach { (month, entries) ->
                combinedList.add(EntryListData.MonthHeader(month))
                entries.forEach { entry ->
                    combinedList.add(EntryListData.EntryItem(entry))
                }
            }

            LoadResult.Page(
                data = combinedList,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (entries.isEmpty()) null else page + 1
            )
        } catch (exception: Exception) {
            Timber.tag(TIMBER_TAG).d("exception - ${exception.message}")
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, EntryListData>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}