package com.judahben149.spendr.presentation.cashflow_summary

import com.judahben149.spendr.domain.model.CashEntry

data class CashFlowSummaryUiState(
    val cashEntryList: List<CashEntry> = emptyList(),
    val isLoading: Boolean = true,
    val isError: Boolean = false
)
