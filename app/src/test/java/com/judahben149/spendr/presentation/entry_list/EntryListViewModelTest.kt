package com.judahben149.spendr.presentation.entry_list

import androidx.paging.PagingData
import com.judahben149.spendr.MainDispatcherRule
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.google.common.truth.Truth.assertThat
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.EntryListData
import com.judahben149.spendr.domain.model.MonthHeader
import io.mockk.coEvery
import io.mockk.every
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest

@OptIn(ExperimentalCoroutinesApi::class)
class EntryListViewModelTest {

    private val repository: CashFlowRepositoryImpl = mockk(relaxed = true)
    private lateinit var viewModel: EntryListViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = EntryListViewModel(repository)
    }

    @Test
    fun `test updateEntryListType updates entry list type in state`() {
        val entryListType = EntryListType.IncomeEntry

        viewModel.updateEntryListType(entryListType)
        val state = viewModel.state.value

        assertThat(state?.entryListType).isEqualTo(entryListType)
    }
    
//    @Test
//    fun `test pagedCashEntries returns expected data`() = runTest {
//        val incomeData = createTestPagingData()
////        val expenditureData = createTestPagingData()
//
//        coEvery { viewModel.pagedCashEntries } returns flow { incomeData }
//
////        every {
////            viewModel.pagedCashEntries
////        } returns flowOf(expenditureData)
//
//        val result: MutableList<PagingData<EntryListData>> = mutableListOf()
//        viewModel.pagedCashEntries.collectLatest { data -> result.add(data) }
//
//        assertThat(result.size).isEqualTo(1)
//    }

//    private fun createTestPagingData(): PagingData<EntryListData> {
//
//        val entriesList: List<EntryListData> = listOf(
//            EntryListData.MonthHeader("January"),
//            EntryListData.EntryItem(CashEntry()),
//            EntryListData.EntryItem(CashEntry(id = 1)),
//        )
//        return PagingData.from(entriesList)
//    }
}