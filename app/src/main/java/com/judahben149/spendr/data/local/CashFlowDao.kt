package com.judahben149.spendr.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.judahben149.spendr.data.local.entity.CashEntryEntity

@Dao
interface CashFlowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)
}