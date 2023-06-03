package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category

interface CashEntryMapper {

    fun cashEntryToCashEntryEntity(cashEntry: CashEntry): CashEntryEntity

    fun cashEntryEntityToCashEntry(cashEntryEntity: CashEntryEntity): CashEntry

    fun categoryToCategoryEntity(category: Category): CategoryEntity

    fun categoryEntityToCategory(categoryEntity: CategoryEntity): Category

    fun mapCategoryEntityListToCategoryList(entityList: List<CategoryEntity>): List<Category>
}