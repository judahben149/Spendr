package com.judahben149.spendr.presentation.entry_list

import androidx.paging.PagingData
import com.judahben149.spendr.domain.model.CashEntry

data class EntryListState(
    val entryPagedList: PagingData<CashEntry>? = null,
    val entryListType: EntryListType = EntryListType.AllEntry
)

sealed class EntryListType {
    object IncomeEntry: EntryListType()
    object ExpenditureEntry: EntryListType()
    object AllEntry: EntryListType()
}
