package com.judahben149.spendr.presentation.cashflow_summary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CashFlowSummaryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl): ViewModel() {

    private var _state: MutableLiveData<CashFlowSummaryUiState> = MutableLiveData(CashFlowSummaryUiState())
    val state: LiveData<CashFlowSummaryUiState> get() = _state

    fun getCashEntries() {
        setIsLoadingState(true)

        viewModelScope.launch {
            repository.getALlCashEntries().collect { cashEntryEntityList ->
                val cashEntryList = cashEntryEntityList.map { cashEntryEntity ->
                    MapperImpl().cashEntryEntityToCashEntry(cashEntryEntity)
                }

                _state.value = _state.value!!.copy(
                    cashEntryList = cashEntryList,
                    isLoading = false
                )
            }
        }
    }

    fun setIsLoadingState(isLoading: Boolean) {
        _state.value = _state.value!!.copy(
            isLoading = isLoading
        )
    }
}