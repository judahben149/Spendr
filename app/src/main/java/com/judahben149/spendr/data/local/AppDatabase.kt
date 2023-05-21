package com.judahben149.spendr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity

@Database(entities = [CashEntryEntity::class, CategoryEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun CashFlowDao(): CashFlowDao
}