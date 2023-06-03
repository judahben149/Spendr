package com.judahben149.spendr.presentation.entry_list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.EntryListData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject constructor(private val cashFlowRepository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<EntryListState> = MutableLiveData(EntryListState())
    val state: LiveData<EntryListState> get() = _state

    val pagedCashEntries: Flow<PagingData<EntryListData>> =
        combine(
            cashFlowRepository.getAllPagedIncome(),
            cashFlowRepository.getAllPagedExpenditure()
        ) { incomeData, expenditureData ->

            when(state.value?.entryListType) {
                is EntryListType.IncomeEntry -> incomeData

                is EntryListType.ExpenditureEntry -> expenditureData

                else ->  PagingData.empty()
                //figure out how to return the full list by merging both income and expenditure here

            }
        }.cachedIn(viewModelScope)


    private fun updatePagedListState(pagedList: PagingData<CashEntry>) {
        _state.value = _state.value!!.copy(
            entryPagedList = pagedList
        )
    }

    fun updateEntryListType(entryListType: EntryListType) {
        _state.value = _state.value!!.copy(
            entryListType = entryListType
        )
    }
}