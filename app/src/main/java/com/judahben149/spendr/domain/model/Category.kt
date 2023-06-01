package com.judahben149.spendr.domain.model

data class Category(
    var categoryId: Int = 0,
    val categoryName: String = "",
    val categoryIconId: Int = 0,
    val isIncomeCategory: Boolean = false
)
