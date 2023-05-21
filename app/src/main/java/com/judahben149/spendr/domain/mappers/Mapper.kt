package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.domain.model.CashEntry

interface Mapper {

    fun cashEntryToCashEntryEntity(cashEntry: CashEntry): CashEntryEntity
}