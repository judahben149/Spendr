package com.judahben149.spendr.domain.model

import com.judahben149.spendr.data.local.entity.CashEntryEntity

sealed class EntryListDataModel {
    data class MonthHeader(val month: String) : EntryListData()
    data class EntryItem(val entry: CashEntry) : EntryListData()
}