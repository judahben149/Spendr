package com.judahben149.spendr.presentation.extras.reminders

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.judahben149.spendr.data.repository.RemindersRepositoryImpl
import com.judahben149.spendr.domain.mappers.RemindersMapperImpl
import com.judahben149.spendr.domain.model.Reminder
import com.judahben149.spendr.utils.Constants.NOTIFICATION_BUNDLE
import com.judahben149.spendr.utils.Constants.REMINDER_TEXT
import com.judahben149.spendr.utils.Constants.REMINDER_TITLE
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.NotificationHelper
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class RemindersViewModel @Inject constructor(
    private val remindersRepository: RemindersRepositoryImpl,
    private val remindersScheduler: RemindersScheduler,
    private val notificationHelper: NotificationHelper,
    private val context: Context
) : ViewModel() {

    private var _reminderListState: MutableLiveData<RemindersUiState> =
        MutableLiveData(RemindersUiState())
    val reminderListState: LiveData<RemindersUiState> get() = _reminderListState

    private var _newReminderState: MutableLiveData<Reminder> = MutableLiveData(Reminder())
    val newReminderState: LiveData<Reminder> get() = _newReminderState


    @RequiresApi(Build.VERSION_CODES.O)
    fun saveReminder(amount: Double, subject: String) {

        if (notificationHelper.isNotificationPermissionGranted()) {
            scheduleReminder(
                "Make payment for $subject",
                amount.abbreviateNumber(),
                _newReminderState.value!!.targetDate
            )

            viewModelScope.launch {
                val reminder =
                    _newReminderState.value?.copy(amount = amount, reminderText = subject)

                reminder?.let {
                    remindersRepository.saveReminder(
                        RemindersMapperImpl().reminderToReminderEntity(reminder)
                    )
                }
            }
        } else {
            notificationHelper.requestNotificationPermission()
        }
    }


    fun getReminders() {
        viewModelScope.launch {
            remindersRepository.getAllReminders().collect { reminderList ->
                val reminders = reminderList.map { reminderEntity ->
                    RemindersMapperImpl().reminderEntityToReminder(reminderEntity)
                }

                _reminderListState.value = _reminderListState.value?.copy(
                    reminders = reminders ?: emptyList()
                )
            }
        }
    }

    fun scheduleReminder(notificationTitle: String, notificationContent: String, time: Long) {
        val bundle = Bundle()
        bundle.putString(REMINDER_TITLE, notificationTitle)
        bundle.putString(REMINDER_TEXT, notificationContent)

        val intent = Intent(context, RemindersReceiver::class.java)
        intent.putExtra(NOTIFICATION_BUNDLE, bundle)
        remindersScheduler.scheduleReminder(time, intent)
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