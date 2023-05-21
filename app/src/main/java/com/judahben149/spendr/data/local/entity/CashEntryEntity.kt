package com.judahben149.spendr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "transaction")
data class CashEntryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val isIncome: Boolean,
    val tag: String,
    val transactionDate: Long
)