package com.judahben149.spendr.domain.model

data class CashEntry(
    val id: Int = 0,
    val amount: Double = 0.00,
    val isIncome: Boolean = false,
    val categoryId: Int = 0,
    val transactionDate: Long = 0L,
    val categoryName: String = "",
    val categoryIconId: Int = 0,
    val isIncomeCategory: Boolean = false
)
