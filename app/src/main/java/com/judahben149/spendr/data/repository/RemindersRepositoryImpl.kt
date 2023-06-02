package com.judahben149.spendr.data.repository

import com.judahben149.spendr.data.local.RemindersDao
import com.judahben149.spendr.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RemindersRepositoryImpl @Inject constructor(private val remindersDao: RemindersDao): RemindersRepository {

    override suspend fun saveReminder(reminderEntity: ReminderEntity) {
        remindersDao.saveReminder(reminderEntity)
    }

    override suspend fun getAllReminders(): Flow<List<ReminderEntity>> {
        return remindersDao.getAllReminders()
    }
}