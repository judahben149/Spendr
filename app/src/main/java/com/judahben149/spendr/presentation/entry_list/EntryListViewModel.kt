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
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EntryListViewModel @Inject constructor(private val cashFlowRepository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<EntryListState> = MutableLiveData(EntryListState())
    val state: LiveData<EntryListState> get() = _state

    val pagedCashEntries: Flow<PagingData<CashEntry>> = cashFlowRepository.getALlPagedCashEntries().cachedIn(viewModelScope).map { pagedList ->
        pagedList.map {  cashEntryEntity ->
            MapperImpl().cashEntryEntityToCashEntry(cashEntryEntity)
        }
    }

    fun getEntryPagedList() {
        viewModelScope.launch {
            val entryPagedListLiveData =
                cashFlowRepository.getALlPagedCashEntries().cachedIn(viewModelScope)
                    .asLiveData()

            val entryPagedList = entryPagedListLiveData.value?.map { cashEntryEntity ->
                MapperImpl().cashEntryEntityToCashEntry(cashEntryEntity)
            }

            entryPagedList?.let { pagedList ->
                updatePagedListState(pagedList)
            }
        }
    }

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