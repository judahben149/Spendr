package com.judahben149.spendr.presentation.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.utils.SessionManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: CashFlowRepositoryImpl,
    private val sessionManager: SessionManager
): ViewModel() {

    private var _state: MutableLiveData<HomeUiState> = MutableLiveData(HomeUiState())
    val state: LiveData<HomeUiState> get() = _state

    init {
        updateBalanceVisibility()
    }

    fun getBalance() {
        isBalanceLoading(true)

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
                    outflowBalance = totalOutflow,
                    inflowBalance = totalInflow,
                    isBalanceLoading = false
                )
            }
        }
    }

    fun isBalanceLoading(isBalanceLoading: Boolean) {
        _state.value = _state.value!!.copy(
            isBalanceLoading = isBalanceLoading
        )
    }

    private fun updateBalanceVisibility() {
        _state.value = _state.value!!.copy(
            isBalanceHidden = sessionManager.checkBalanceVisibility()
        )
    }

    fun toggleBalanceVisibility() {
        val isBalanceHidden = sessionManager.checkBalanceVisibility()

        sessionManager.toggleBalanceVisibility(!isBalanceHidden)
        updateBalanceVisibility()
    }
}