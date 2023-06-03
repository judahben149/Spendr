package com.judahben149.spendr.presentation.entry_detail

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
class EntryDetailViewModel @Inject constructor(private val cashFlowRepository: CashFlowRepositoryImpl) :
    ViewModel() {

    private var _state: MutableLiveData<EntryDetailState> = MutableLiveData(EntryDetailState())
    val state: LiveData<EntryDetailState> get() = _state


    fun getEntryDetail(entryId: Int) {
        setLoadingState(true)

        viewModelScope.launch {
            val entryDetailEntity = cashFlowRepository.getEntryDetail(entryId)
            val entryDetail = entryDetailEntity?.let { CashEntryMapperImpl().cashEntryEntityToCashEntry(entryDetailEntity) }

                _state.value = _state.value?.copy(
                    cashEntry = entryDetail,
                    isLoading = false
                )
        }
    }

    private fun setLoadingState(isLoading: Boolean) {
        _state.value = _state.value?.copy(
            isLoading = isLoading
        )
    }
}