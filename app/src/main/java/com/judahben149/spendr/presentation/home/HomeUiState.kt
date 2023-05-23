package com.judahben149.spendr.presentation.home

data class HomeUiState(
    val outflowBalance: Double = 0.00,
    val inflowBalance: Double = 0.00,
    val isBalanceLoading: Boolean = true
)