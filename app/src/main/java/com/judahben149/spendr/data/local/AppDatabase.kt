package com.judahben149.spendr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.data.local.entity.ReminderEntity

@Database(entities = [CashEntryEntity::class, CategoryEntity::class, ReminderEntity::class], exportSchema = false, version = 2)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CashFlowDao(): CashFlowDao

    abstract fun RemindersDao(): RemindersDao
}