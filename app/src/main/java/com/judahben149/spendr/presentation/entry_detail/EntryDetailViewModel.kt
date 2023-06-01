package com.judahben149.spendr.presentation.entry_detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.MapperImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
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
            val entryDetail = MapperImpl().cashEntryEntityToCashEntry(entryDetailEntity)

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