package com.judahben149.spendr.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.judahben149.spendr.data.local.entity.CashEntryEntity

@Database(entities = [CashEntryEntity::class], exportSchema = false, version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun CashFlowDao(): CashFlowDao
}