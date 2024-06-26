package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category

class CashEntryMapperImpl: CashEntryMapper {
    override fun cashEntryToCashEntryEntity(cashEntry: CashEntry): CashEntryEntity {
        return CashEntryEntity(
            id = cashEntry.id,
            amount = cashEntry.amount,
            isIncome = cashEntry.isIncome,
            categoryId = cashEntry.categoryId,
            transactionDate = cashEntry.transactionDate,
            categoryName = cashEntry.categoryName,
            categoryIconId = cashEntry.categoryIconId,
            isIncomeCategory = cashEntry.isIncomeCategory,
            reason = cashEntry.reason
        )
    }

    override fun cashEntryEntityToCashEntry(cashEntryEntity: CashEntryEntity): CashEntry {
        return CashEntry(
            id = cashEntryEntity.id,
            amount = cashEntryEntity.amount,
            isIncome = cashEntryEntity.isIncome,
            categoryId = cashEntryEntity.categoryId,
            transactionDate = cashEntryEntity.transactionDate,
            categoryName = cashEntryEntity.categoryName,
            categoryIconId = cashEntryEntity.categoryIconId,
            isIncomeCategory = cashEntryEntity.isIncomeCategory,
            reason = cashEntryEntity.reason
        )
    }

    override fun categoryToCategoryEntity(category: Category): CategoryEntity {
        return CategoryEntity(
            categoryId = category.categoryId,
            categoryName = category.categoryName,
            categoryIconId = category.categoryIconId,
            isIncomeCategory = category.isIncomeCategory
        )
    }

    override fun categoryEntityToCategory(categoryEntity: CategoryEntity): Category {
        return Category(
            categoryId = categoryEntity.categoryId,
            categoryName = categoryEntity.categoryName,
            categoryIconId = categoryEntity.categoryIconId,
            isIncomeCategory = categoryEntity.isIncomeCategory
        )
    }

    override fun mapCategoryEntityListToCategoryList(entityList: List<CategoryEntity>): List<Category> {
        return entityList.map { categoryEntityToCategory(it) }
    }
}