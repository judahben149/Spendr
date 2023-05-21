package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.domain.model.CashEntry

class MapperImpl: Mapper {
    override fun cashEntryToCashEntryEntity(cashEntry: CashEntry): CashEntryEntity {
        return CashEntryEntity(
            id = cashEntry.id,
            amount = cashEntry.amount,
            isIncome = cashEntry.isIncome,
            categoryId = cashEntry.categoryId,
            transactionDate = cashEntry.transactionDate
        )
    }


}