package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category

interface Mapper {

    fun cashEntryToCashEntryEntity(cashEntry: CashEntry): CashEntryEntity

    fun categoryToCategoryEntity(category: Category): CategoryEntity
}