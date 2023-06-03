package com.judahben149.spendr.presentation.add_cash_entry

import com.judahben149.spendr.domain.model.Category

data class AddCashEntryState(
    val amount: Int = 0,
    val isIncome: Boolean = false,
    val categoryId: Int = 0,
    val date: Long = 0L,
    val categoryName: String = "",
    val categoryIconId: Int = 0,
    val isIncomeCategory: Boolean = false

)

data class CategoryListState(
    val categoryList: List<Category> = emptyList(),
    val isIncomeSelected: Boolean = false,
    val isError: Boolean = false,
    val isLoading: Boolean = false
)
