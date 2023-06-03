package com.judahben149.spendr.presentation.entry_detail

import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category

data class EntryDetailState(
    val cashEntry: CashEntry? = null,
    val isLoading: Boolean = false
)
