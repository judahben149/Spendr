package com.judahben149.spendr.presentation.add_cash_entry

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepository
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCashEntryViewModel @Inject constructor(private val repository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<AddCashEntryState> = MutableLiveData(AddCashEntryState())
    val state: LiveData<AddCashEntryState> = _state

    fun updateEntryType(isIncome: Boolean) {
        _state.value = _state.value?.copy(
            isIncome = isIncome
        )
    }

    fun updateAmount(amount: Double) {
        _state.value = _state.value?.copy(
            amount = amount
        )
    }

    fun saveEntry() {
        viewModelScope.launch {

            val cashEntry = CashEntry(
                amount = _state.value!!.amount,
                isIncome = _state.value!!.isIncome,
            )

            val entity = MapperImpl().cashEntryToCashEntryEntity(cashEntry)
            repository.saveEntry(entity)
        }
    }
}