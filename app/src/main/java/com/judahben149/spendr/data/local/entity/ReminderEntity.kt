package com.judahben149.spendr.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val amount: Double,
    val targetDate: Long,
    val reminderText: String,
    val isRecurrent: Boolean
)