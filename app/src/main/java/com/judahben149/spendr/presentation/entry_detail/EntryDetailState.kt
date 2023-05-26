package com.judahben149.spendr.presentation.entry_detail

import com.judahben149.spendr.domain.model.CashEntry

data class EntryDetailState(
    val cashEntry: CashEntry? = null,
    val isLoading: Boolean = false
)
