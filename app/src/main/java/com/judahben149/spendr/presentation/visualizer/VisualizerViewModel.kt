package com.judahben149.spendr.presentation.visualizer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class VisualizerViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl): ViewModel() {

    private var _state: MutableLiveData<VisualizerUiState> = MutableLiveData(VisualizerUiState())
    val state: LiveData<VisualizerUiState> get() = _state


    fun getBalance() {
        viewModelScope.launch {

            repository.getALlCashEntries().collect { entryList ->
                val allEntries = entryList.map {  entryEntity ->
                    CashEntryMapperImpl().cashEntryEntityToCashEntry(entryEntity)
                }

                var totalInflow = 0.00
                var totalOutflow = 0.00

                val inflow = allEntries.filter { cashEntry ->
                    cashEntry.isIncome == true
                }.onEach {
                    totalInflow = totalInflow + it.amount
                }

                val outflow = allEntries.filter { cashEntry ->
                    cashEntry.isIncome == false
                }.onEach {
                    totalOutflow = totalOutflow + it.amount
                }

                _state.value = _state.value!!.copy(
                    expenditureBalance = totalOutflow,
                    incomeBalance = totalInflow
                )
                setPieChartDataReady(true)
            }
        }
    }


    fun setPieChartDataReady(isPieChartDataReady: Boolean) {
        _state.value = _state.value!!.copy(
            isPieChartDataReady = isPieChartDataReady
        )
    }
}