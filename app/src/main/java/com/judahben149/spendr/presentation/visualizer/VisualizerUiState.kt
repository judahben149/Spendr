package com.judahben149.spendr.presentation.visualizer

data class VisualizerUiState(
    val isPieChartDataReady: Boolean = false,
    val incomeBalance: Double = 0.00,
    val expenditureBalance: Double = 0.00,
)