package com.judahben149.spendr.presentation.cashflow_summary.epoxy

import android.content.Context
import android.util.Log
import androidx.annotation.Keep
import com.airbnb.epoxy.TypedEpoxyController
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.cashflow_summary.CashFlowSummaryUiState
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryCardEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryEntryItemEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryExpenditureHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryIncomeHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryProgressBarEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummarySpacer
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber

@Keep
class SummaryEpoxyController(
    val context: Context,
    val onSeeAllIncomeClicked: () -> Unit,
    val onSeeAllExpenditureClicked: () -> Unit,
    val onEntryItemClicked: (id: Int) -> Unit
) : TypedEpoxyController<CashFlowSummaryUiState>() {


    override fun buildModels(uiState: CashFlowSummaryUiState?) {

        //set loading state
        if (uiState == null || uiState.isLoading) {
            SummaryProgressBarEpoxyModel().id("pgBar").addTo(this)
            return
        }

        //set empty list state
        if (uiState.cashEntryList.isNullOrEmpty()) {
            SummaryCardEpoxyModel("0", "0", "_").id("card").addTo(this)
            return
        }

        val income = getTotalIncome(uiState.cashEntryList).abbreviateNumber(context)
        val expenditure = getTotalExpenditure(uiState.cashEntryList).abbreviateNumber(context)
        val latestThreeIncome = getLatestThreeIncomes(uiState.cashEntryList)
        val latestThreeExpenditure = getLatestThreeExpenditures(uiState.cashEntryList)

        SummaryCardEpoxyModel(income, expenditure, "All").id("card_summary").addTo(this)

        SummaryIncomeHeaderEpoxyModel(
            incomeTitle = "Income",
            onSeeAllIncomeClicked = {
                onSeeAllIncomeClicked()
            }
        ).id(-3).addTo(this)

        latestThreeIncome.forEach { cashEntry ->
            SummaryEntryItemEpoxyModel(
                context,
                cashEntry = cashEntry
            ) {
                onEntryItemClicked(cashEntry.id)
            }.id("Income_${cashEntry.id}").addTo(this)
        }

//        SummarySpacer(spacerHeight = 100)

        SummaryExpenditureHeaderEpoxyModel(
            "Expenditure"
        ) {
            onSeeAllExpenditureClicked()
        }.id(-4).addTo(this)

        latestThreeExpenditure.forEach { cashEntry ->
            SummaryEntryItemEpoxyModel(
                context,
                cashEntry = cashEntry
            ) {
                onEntryItemClicked(cashEntry.id)
            }.id("Expenditure_${cashEntry.id}").addTo(this)
        }
    }


    private fun getTotalIncome(cashEntryList: List<CashEntry>): Double {
        var totalIncome = 0.00

        cashEntryList.filter { cashEntry ->
            cashEntry.isIncome
        }.map { cashEntry ->
            totalIncome += cashEntry.amount
        }
        Log.d("TAGM", "getTotalIncome: $totalIncome")
        return totalIncome
    }

    private fun getTotalExpenditure(cashEntryList: List<CashEntry>): Double {
        var totalExpenditure = 0.00

        cashEntryList.filter { cashEntry ->
            !cashEntry.isIncome
        }.map { cashEntry ->
            totalExpenditure += cashEntry.amount
        }
        Log.d("TAGM", "getTotalExpenditure: $totalExpenditure")
        return totalExpenditure
    }

    private fun getLatestThreeIncomes(cashEntryList: List<CashEntry>): List<CashEntry> {
        val sortedList = cashEntryList.filter { cashEntry ->
            cashEntry.isIncome
        }.sortedByDescending {
            it.transactionDate
        }

        Log.d("TAGM", "getLastThreeIncomes: ${sortedList.take(3)}")
        return sortedList.take(3)
    }

    private fun getLatestThreeExpenditures(cashEntryList: List<CashEntry>): List<CashEntry> {
        val sortedList = cashEntryList.filter { cashEntry ->
            !cashEntry.isIncome
        }.sortedByDescending {
            it.transactionDate
        }

        Log.d("TAGM", "getLastThreeExpenditures: ${sortedList.take(3)}")
        return sortedList.take(3)
    }

}