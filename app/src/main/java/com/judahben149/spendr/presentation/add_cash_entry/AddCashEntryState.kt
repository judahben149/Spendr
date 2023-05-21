package com.judahben149.spendr.presentation.add_cash_entry

data class AddCashEntryState(
    val amount: Double = 0.00,
    val isIncome: Boolean = false,
    val categoryId: Int = 0
)