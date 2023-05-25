package com.judahben149.spendr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cashEntry")
data class CashEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val isIncome: Boolean,
    val categoryId: Int,
    val transactionDate: Long
)