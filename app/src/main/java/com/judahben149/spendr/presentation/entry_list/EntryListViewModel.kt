package com.judahben149.spendr.presentation.entry_list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.EntryListData
import com.judahben149.spendr.domain.model.EntryListDataModel
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
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