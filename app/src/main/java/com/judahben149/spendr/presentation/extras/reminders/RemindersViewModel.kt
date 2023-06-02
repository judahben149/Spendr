package com.judahben149.spendr.presentation.extras.reminders

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.RemindersRepositoryImpl
import com.judahben149.spendr.domain.mappers.RemindersMapperImpl
import com.judahben149.spendr.domain.model.Reminder
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(private val remindersRepository: RemindersRepositoryImpl): ViewModel() {

    private var _reminderListState: MutableLiveData<RemindersUiState> = MutableLiveData(RemindersUiState())
    val reminderListState: LiveData<RemindersUiState> get() = _reminderListState

    private var _newReminderState: MutableLiveData<Reminder> = MutableLiveData(Reminder())
    val newReminderState: LiveData<Reminder> get() = _newReminderState

    fun saveReminder(amount: Double, subject: String) {
        viewModelScope.launch {
            val reminder = _newReminderState.value?.copy(amount = amount, reminderText = subject)
            Timber.tag(TIMBER_TAG).d("save reminder - viewModel")

            reminder?.let {
                remindersRepository.saveReminder(
                    RemindersMapperImpl().reminderToReminderEntity(reminder)
                )
            }
        }
    }


    fun getReminders() {
        viewModelScope.launch {
            remindersRepository.getAllReminders().collect { reminderList ->
                val reminders = reminderList.map {  reminderEntity ->
                    RemindersMapperImpl().reminderEntityToReminder(reminderEntity)
                }

                _reminderListState.value = _reminderListState.value?.copy(
                    reminders = reminders ?: emptyList()
                )
            }
        }
    }

    fun setIsRecurrent(isRecurrent: Boolean) {
        _newReminderState.value = _newReminderState.value?.copy(
            isRecurrent = isRecurrent
        )
    }

    fun setTargetDate(targetDate: Long) {
        _newReminderState.value = _newReminderState.value?.copy(
            targetDate = targetDate
        )
    }

    fun setAmount(amount: Double) {
        _newReminderState.value = _newReminderState.value?.copy(
            amount = amount
        )
    }

    fun setReminderText(reminderText: String) {
        _newReminderState.value = _newReminderState.value?.copy(
            reminderText = reminderText
        )
    }

    fun setReminderId(reminderId: Int) {
        _newReminderState.value = _newReminderState.value?.copy(
            id = reminderId
        )
    }

    fun reset() {
        _newReminderState.value = Reminder()
    }
}