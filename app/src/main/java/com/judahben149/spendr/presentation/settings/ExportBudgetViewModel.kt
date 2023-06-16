package com.judahben149.spendr.presentation.settings

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepository
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ExportBudgetViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl) :
    ViewModel() {

    private val _state: MutableLiveData<ExportBudgetUiState> =
        MutableLiveData(ExportBudgetUiState())
    val state: LiveData<ExportBudgetUiState> get() = _state

    fun getAllBudgetEntries() {
        viewModelScope.launch {
            repository.getALlCashEntries().collect { cashEntryEntityList ->
                val cashEntryList = cashEntryEntityList.map {
                    CashEntryMapperImpl().cashEntryEntityToCashEntry(it)
                }

                _state.value = _state.value?.copy(
                    budgetEntries = cashEntryList,
                    isExportComplete = true
                )
            }
        }
    }
}

data class ExportBudgetUiState(
    val budgetEntries: List<CashEntry> = emptyList(),
    val isExportComplete: Boolean = false
)