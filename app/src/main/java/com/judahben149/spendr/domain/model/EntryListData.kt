package com.judahben149.spendr.domain.model

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl

sealed class EntryListData {
    data class MonthHeader(val month: String) : EntryListData()
    data class EntryItem(val entryItem: CashEntry) : EntryListData() {
        constructor(entity: CashEntryEntity) : this(convertEntityToModel(entity))

        private companion object {
            private fun convertEntityToModel(entity: CashEntryEntity): CashEntry {
                return CashEntryMapperImpl().cashEntryEntityToCashEntry(entity)
            }
        }
    }
}
