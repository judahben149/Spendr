package com.judahben149.spendr.presentation.extras.reminders

import com.judahben149.spendr.domain.model.Reminder

data class RemindersUiState(
    val reminders: List<Reminder> = emptyList()
)
