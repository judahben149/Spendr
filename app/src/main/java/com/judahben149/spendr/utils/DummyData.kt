package com.judahben149.spendr.utils

import com.judahben149.spendr.domain.model.Category

object DummyData {

    val categoryList = listOf<Category>(
        Category(
            categoryId = 0,
            categoryName = "Food",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 1,
            categoryName = "Savings",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 2,
            categoryName = "Education",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 3,
            categoryName = "Automobile",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 4,
            categoryName = "Gift",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 5,
            categoryName = "Medical",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 6,
            categoryName = "Salary",
            isIncomeCategory = true
        ),
        Category(
            categoryId = 7,
            categoryName = "Subscriptions",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 8,
            categoryName = "Groceries",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 9,
            categoryName = "Tax",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 10,
            categoryName = "Travel",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 11,
            categoryName = "Loan",
            isIncomeCategory = true
        ),
        Category(
            categoryId = 12,
            categoryName = "Rent",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 13,
            categoryName = "Utilities",
            isIncomeCategory = false
        ),
        Category(
            categoryId = 14,
            categoryName = "Miscellaneous",
            isIncomeCategory = false
        )
    )

}