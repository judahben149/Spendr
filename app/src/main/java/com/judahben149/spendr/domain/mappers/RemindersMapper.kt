package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.ReminderEntity
import com.judahben149.spendr.domain.model.Reminder

interface RemindersMapper {

    fun reminderToReminderEntity(reminder: Reminder): ReminderEntity

    fun reminderEntityToReminder(reminderEntity: ReminderEntity): Reminder
}