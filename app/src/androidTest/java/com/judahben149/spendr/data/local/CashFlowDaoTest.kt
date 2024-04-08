package com.judahben149.spendr.data.local

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import app.cash.turbine.test
import com.judahben149.spendr.MainDispatcherRule
import com.judahben149.spendr.data.local.entity.CashEntryEntity
import com.judahben149.spendr.data.local.entity.CategoryEntity
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
class CashFlowDaoTest {

    private lateinit var cashFlowDao: CashFlowDao
    private lateinit var database: AppDatabase

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun create() {
        val context = ApplicationProvider.getApplicationContext<Context>()

        database = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        cashFlowDao = database.CashFlowDao()
    }

    @After
    fun cleanUp() {
        database.close()
    }

    @Test
    fun saveEntry_shouldReturnSavedEntryItem() = runTest {
        val entry = CashEntryEntity(
            id = 10,
            amount = 123.0,
            isIncome = true,
            categoryId = 12,
            transactionDate = 1200000L,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = true,
            reason = "Some reason"
        )

        cashFlowDao.saveEntry(entry)

        cashFlowDao.getAllCashEntries().test {
            val list = awaitItem()
            assert(list.contains(entry))
            cancel()
        }
    }

    @Test
    fun saveNewCategory_shouldReturnSameCategoryItem() = runTest {
        val category = CategoryEntity(
            categoryId = 12,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = true
        )

        cashFlowDao.saveNewCategory(category)

        cashFlowDao.getCategories().test {
            val list = awaitItem()
            assert(list.contains(category))
            cancel()
        }
    }

    @Test
    fun getEntryDetail_shouldReturnSameItem() = runTest {
        val entry = CashEntryEntity(
            id = 10,
            amount = 123.0,
            isIncome = true,
            categoryId = 12,
            transactionDate = 1200000L,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = true,
            reason = "Some reason"
        )

        cashFlowDao.saveEntry(entry)

        val expectedEntry = cashFlowDao.getEntryDetail(entry.id)
        assert(expectedEntry == entry)
    }

    @Test
    fun deleteAllEntries_ShouldReturnEmptyList() = runTest {
        val entry = CashEntryEntity(
            id = 10,
            amount = 123.0,
            isIncome = true,
            categoryId = 12,
            transactionDate = 1200000L,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = true,
            reason = "Some reason"
        )

        val entry2 = CashEntryEntity(
            id = 14,
            amount = 126.5,
            isIncome = false,
            categoryId = 13,
            transactionDate = 12056700L,
            categoryName = "Charity",
            categoryIconId = 16,
            isIncomeCategory = false,
            reason = "Some reason"
        )

        cashFlowDao.saveEntry(entry)
        cashFlowDao.saveEntry(entry2)

        cashFlowDao.getAllCashEntries().test {
            val list = awaitItem()
            assert(list.size == 2)
            cancel()
        }

        cashFlowDao.deleteAllEntries()

        cashFlowDao.getAllCashEntries().test {
            val list = awaitItem()
            assert(list.isEmpty())
            cancel()
        }
    }

    @Test
    fun getAllPagedExpenditure_shouldReturnCorrectData() = runTest {
        val entry = CashEntryEntity(
            id = 1,
            amount = 100.0,
            isIncome = true,
            categoryId = 12,
            transactionDate = 1200000L,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = true,
            reason = "Some reason"
        )

        val expectedList = listOf(
            entry,
            entry.copy(id = 2, amount = 200.0),
            entry.copy(id = 3, amount = 300.0),
            entry.copy(id = 4, amount = 400.0),
            entry.copy(id = 5, amount = 500.0),
            entry.copy(id = 6, amount = 600.0),
            entry.copy(id = 7, amount = 700.0),
        )

        expectedList.forEach {
            cashFlowDao.saveEntry(it)
        }

        val offset = 0
        val limit = 10

        val resultList = cashFlowDao.getAllPagedIncome(limit, offset)
        assert(expectedList == resultList)
    }

    @Test
    fun getAllPagedIncome_shouldReturnCorrectData() = runTest {
        val entry = CashEntryEntity(
            id = 1,
            amount = 100.0,
            isIncome = false,
            categoryId = 12,
            transactionDate = 1200000L,
            categoryName = "Salary",
            categoryIconId = 11,
            isIncomeCategory = false,
            reason = "Some reason"
        )

        val expectedList = listOf(
            entry,
            entry.copy(id = 2, amount = 200.0),
            entry.copy(id = 3, amount = 300.0),
            entry.copy(id = 4, amount = 400.0),
            entry.copy(id = 5, amount = 500.0),
            entry.copy(id = 6, amount = 600.0),
            entry.copy(id = 7, amount = 700.0),
        )

        expectedList.forEach {
            cashFlowDao.saveEntry(it)
        }

        val offset = 0
        val limit = 10

        val resultList = cashFlowDao.getAllPagedExpenditure(limit, offset)
        assert(expectedList == resultList)
    }
}