package com.judahben149.spendr.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.judahben149.spendr.MainDispatcherRule
import com.judahben149.spendr.data.local.entity.ReminderEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@SmallTest
@OptIn(ExperimentalCoroutinesApi::class)
class RemindersDaoTest {

    private lateinit var database: AppDatabase
    private lateinit var remindersDao: RemindersDao

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        remindersDao = database.RemindersDao()
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveReminder_shouldReturnSavedReminderItem() = runTest {
        val reminder = ReminderEntity(
            id = 1,
            amount = 10.0,
            targetDate = 1000000L,
            reminderText = "Placeholder reminder text",
            isRecurrent = true
        )

        remindersDao.saveReminder(reminder)

        remindersDao.getAllReminders().test {
            val reminderList = awaitItem()
            assert(reminderList.contains(reminder))
            cancel()
        }
    }

    @Test
    fun deleteExpiredReminders_shouldDeleteOnlyExpiredReminders() = runTest {
        val targetDate = 1000000L

        val reminder = ReminderEntity(
            id = 1,
            amount = 10.0,
            targetDate = targetDate + 1,
            reminderText = "Placeholder reminder text",
            isRecurrent = true
        )

        val reminder2 = reminder.copy(id = 3, targetDate = targetDate + 2)
        val reminder3 = reminder.copy(id = 2, targetDate = targetDate - 1)
        val reminder4 = reminder.copy(id = 4, targetDate = targetDate - 2)

        remindersDao.saveReminder(reminder)
        remindersDao.saveReminder(reminder2)
        remindersDao.saveReminder(reminder3)
        remindersDao.saveReminder(reminder4)

        remindersDao.deleteExpiredReminders(targetDate)

        remindersDao.getAllReminders().test {
            val reminderList = awaitItem()

            assert(reminderList.size == 2)
            assert(reminderList.contains(reminder))
            assert(reminderList.contains(reminder2))
            assert(!reminderList.contains(reminder3))
            assert(!reminderList.contains(reminder4))

            cancel()
        }
    }

    @Test
    fun deleteAllReminders_shouldReturnEmptyList() = runTest {
        val targetDate = 1000000L

        val reminder = ReminderEntity(
            id = 1,
            amount = 10.0,
            targetDate = targetDate + 1,
            reminderText = "Placeholder reminder text",
            isRecurrent = true
        )

        val reminder2 = reminder.copy(id = 3, targetDate = targetDate + 2)
        val reminder3 = reminder.copy(id = 2, targetDate = targetDate - 1)
        val reminder4 = reminder.copy(id = 4, targetDate = targetDate - 2)

        remindersDao.saveReminder(reminder)
        remindersDao.saveReminder(reminder2)
        remindersDao.saveReminder(reminder3)
        remindersDao.saveReminder(reminder4)

        remindersDao.deleteAllReminders()

        remindersDao.getAllReminders().test {
            val reminderList = awaitItem()

            assert(reminderList.isEmpty())
            cancel()
        }
    }


}