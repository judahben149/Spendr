package com.judahben149.spendr.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveEntry(cashEntryEntity: CashEntryEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun saveNewCategory(categoryEntity: CategoryEntity)

    @Query("SELECT * FROM category ORDER BY categoryName")
    fun getCategories(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM cashEntry ORDER BY transactionDate DESC")
    fun getAllCashEntries(): Flow<List<CashEntryEntity>>

    @Query("SELECT * FROM cashEntry ORDER BY transactionDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getAllPagedCashEntries(limit: Int, offset: Int): List<CashEntryEntity>

    @Query("SELECT * FROM cashEntry WHERE isIncome = 1 ORDER BY transactionDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getAllPagedIncome(limit: Int, offset: Int): List<CashEntryEntity>

    @Query("SELECT * FROM cashEntry WHERE isIncome = 0 ORDER BY transactionDate DESC LIMIT :limit OFFSET :offset")
    suspend fun getAllPagedExpenditure(limit: Int, offset: Int): List<CashEntryEntity>


    @Query("SELECT * FROM cashEntry WHERE id = :entryId")
    suspend fun getEntryDetail(entryId: Int): CashEntryEntity
}