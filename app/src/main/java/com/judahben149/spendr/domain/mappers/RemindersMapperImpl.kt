package com.judahben149.spendr.domain.mappers

import com.judahben149.spendr.data.local.entity.ReminderEntity
import com.judahben149.spendr.domain.model.Reminder

class RemindersMapperImpl : RemindersMapper {

    override fun reminderToReminderEntity(reminder: Reminder): ReminderEntity {
        return ReminderEntity(
            id = reminder.id,
            amount = reminder.amount,
            targetDate = reminder.targetDate,
            reminderText = reminder.reminderText,
            isRecurrent = reminder.isRecurrent
        )
    }

    override fun reminderEntityToReminder(reminderEntity: ReminderEntity): Reminder {
        return Reminder(
            id = reminderEntity.id,
            amount = reminderEntity.amount,
            targetDate = reminderEntity.targetDate,
            reminderText = reminderEntity.reminderText,
            isRecurrent = reminderEntity.isRecurrent
        )
    }
}