package com.judahben149.spendr.presentation.home

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
class HomeViewModelTest {

    private val repository: CashFlowRepositoryImpl = mockk()
    private lateinit var viewModel: HomeViewModel

    @get:Rule
    val rule = MainDispatcherRule()

    @Before
    fun setup() {
        viewModel = HomeViewModel(repository)
    }

    @Test
    fun `test isBalanceLoading() updates state correctly`() {
        val expectedIsBalanceLoading = true

        viewModel.isBalanceLoading(expectedIsBalanceLoading)

        val state = viewModel.state.value
        assertEquals(expectedIsBalanceLoading, state?.isBalanceLoading)
    }

    @Test
    fun `test getBalance() updates state correctly`() = runTest {
        val entries = listOf<CashEntry>(
            CashEntry(id = 0, amount = 10.0, isIncome = true),
            CashEntry(id = 1, amount = 10.0, isIncome = false),
            CashEntry(id = 2, amount = 10.0, isIncome = true),
            CashEntry(id = 3, amount = 10.0, isIncome = false),
            CashEntry(id = 4, amount = 10.0, isIncome = true),
        )

        val expectedInflowBalance = 30.0
        val expectedOutflowBalance = 20.0
        val expectedIsBalanceLoading = false

        coEvery { repository.getALlCashEntries() } returns flow {
            emit(
                entries.map {
                    CashEntryMapperImpl().cashEntryToCashEntryEntity(it)
                }
            )
        }

        viewModel.getBalance()

        val state = viewModel.state.value
        assert(state?.isBalanceLoading == expectedIsBalanceLoading)
        assert(state?.inflowBalance == expectedInflowBalance)
        assert(state?.outflowBalance == expectedOutflowBalance)
    }
}