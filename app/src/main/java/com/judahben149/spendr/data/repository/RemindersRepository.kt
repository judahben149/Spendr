package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface RemindersRepository {

    suspend fun saveReminder(reminderEntity: ReminderEntity)

    suspend fun getAllReminders(): Flow<List<ReminderEntity>>
}