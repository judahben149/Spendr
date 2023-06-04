package com.judahben149.spendr.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.judahben149.spendr.data.local.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RemindersDao {

    @Upsert()
    suspend fun saveReminder(reminderEntity: ReminderEntity)

    @Query("SELECT * FROM reminders ORDER BY targetDate ASC")
    fun getAllReminders(): Flow<List<ReminderEntity>>
}