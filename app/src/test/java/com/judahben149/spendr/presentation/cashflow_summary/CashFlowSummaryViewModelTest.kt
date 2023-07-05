package com.judahben149.spendr.presentation.cashflow_summary

import com.judahben149.spendr.MainDispatcherRule
import com.judahben149.spendr.data.repository.CashFlowRepositoryImpl
import com.judahben149.spendr.domain.mappers.CashEntryMapperImpl
import com.judahben149.spendr.domain.model.CashEntry
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CashFlowSummaryViewModelTest {

    private val repository: CashFlowRepositoryImpl = mockk()
    private lateinit var viewModel: CashFlowSummaryViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setUp() {
        viewModel = CashFlowSummaryViewModel(repository)
    }

    @Test
    fun `test setIsLoadingState() updates state correctly`() {
        val expectedIsBalanceLoading = true

        viewModel.setIsLoadingState(expectedIsBalanceLoading)

        val state = viewModel.state.value
        assertEquals(expectedIsBalanceLoading, state?.isLoading)
    }

    @Test
    fun `test getCashEntries() updates state correctly`() = runTest {
        val entries = listOf(
            CashEntry(id = 0, amount = 10.0, isIncome = true),
            CashEntry(id = 1, amount = 10.0, isIncome = false),
            CashEntry(id = 2, amount = 10.0, isIncome = true),
            CashEntry(id = 3, amount = 10.0, isIncome = false),
            CashEntry(id = 4, amount = 10.0, isIncome = true),
        )

        coEvery { repository.getALlCashEntries() } returns flow {
            emit(
                entries.map {
                    CashEntryMapperImpl().cashEntryToCashEntryEntity(it)
                }
            )
        }

        viewModel.getCashEntries()

        val state = viewModel.state.value
        assert(state?.cashEntryList == entries)
    }
}