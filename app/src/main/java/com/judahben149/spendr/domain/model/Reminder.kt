package com.judahben149.spendr.domain.model

data class Reminder(
    val id: Int = 0,
    val amount: Double = 0.0,
    val targetDate: Long = 0L,
    val reminderText: String = "",
    val isRecurrent: Boolean = false
)
