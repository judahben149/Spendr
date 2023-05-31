package com.judahben149.spendr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val categoryId: Int,
    val categoryName: String,
    val categoryIconId: Int,
    val isIncomeCategory: Boolean
)
