package com.judahben149.spendr.presentation.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(private val repositoryImpl: CashFlowRepositoryImpl): ViewModel() {

    fun deleteAllEntries() {
        viewModelScope.launch {
            repositoryImpl.deleteAllEntries()
        }
    }

    fun deleteAllReminders() {
        viewModelScope.launch {
            repositoryImpl.deleteAllReminders()
        }
    }

    fun deleteExpiredReminders(currentDate: Long) {
        viewModelScope.launch {
            repositoryImpl.deleteExpiredReminders(currentDate)
        }
    }
}