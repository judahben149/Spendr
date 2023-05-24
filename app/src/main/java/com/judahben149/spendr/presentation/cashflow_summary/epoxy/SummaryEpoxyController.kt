package com.judahben149.spendr.presentation.cashflow_summary.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryCardEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryEntryItemEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryExpenditureHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryIncomeHeaderEpoxyModel
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummarySpacer
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber

class SummaryEpoxyController: TypedEpoxyController<List<CashEntry>>() {


    override fun buildModels(data: List<CashEntry>?) {
        if (data.isNullOrEmpty()) {
            SummaryHeaderEpoxyModel("Summary").id(1).addTo(this)
            SummaryCardEpoxyModel("300", "500", "January").id(2).addTo(this)
            return
        }

        val income = getTotalIncome(data!!).abbreviateNumber()
        val expenditure = getTotalExpenditure(data!!).abbreviateNumber()
        val latestThreeIncome = getLastThreeIncomes(data!!)
        val latestThreeExpenditure = getLastThreeExpenditures(data!!)

        SummaryHeaderEpoxyModel("Summary").id(-1).addTo(this)
        SummaryCardEpoxyModel(income, expenditure, "July").id(-2).addTo(this)
        SummaryIncomeHeaderEpoxyModel("Income").id(-3).addTo(this)

        latestThreeIncome.forEach { cashEntry ->
            SummaryEntryItemEpoxyModel(
                amount = cashEntry.amount.abbreviateNumber(),
                date = DateUtils.formatFriendlyDateTime(cashEntry.transactionDate),
                category = "Salary"
            ).id(cashEntry.id).addTo(this)
        }

//        SummarySpacer(spacerHeight = 100)

        SummaryExpenditureHeaderEpoxyModel("Expenditure").id(-4).addTo(this)

        latestThreeExpenditure.forEach { cashEntry ->
            SummaryEntryItemEpoxyModel(
                amount = cashEntry.amount.abbreviateNumber(),
                date = DateUtils.formatFriendlyDateTime(cashEntry.transactionDate),
                category = "Savings"
            ).id(cashEntry.id).addTo(this)
        }
    }

    private fun getTotalIncome(cashEntryList: List<CashEntry>): Double {
        var totalIncome = 0.00

        cashEntryList.filter { cashEntry ->
            cashEntry.isIncome == true
        }.map { cashEntry ->
            totalIncome = totalIncome + cashEntry.amount
        }
        Log.d("TAGM", "getTotalIncome: $totalIncome")
        return totalIncome
    }

    private fun getTotalExpenditure(cashEntryList: List<CashEntry>): Double {
        var totalExpenditure = 0.00

        cashEntryList.filter { cashEntry ->
            cashEntry.isIncome == false
        }.map { cashEntry ->
            totalExpenditure = totalExpenditure + cashEntry.amount
        }
        Log.d("TAGM", "getTotalExpenditure: $totalExpenditure")
        return totalExpenditure
    }

    private fun getLastThreeIncomes(cashEntryList: List<CashEntry>): List<CashEntry> {
        val sortedList = cashEntryList.filter { cashEntry ->
            cashEntry.isIncome == true
        }.sortedByDescending {
            it.transactionDate
        }

        Log.d("TAGM", "getLastThreeIncomes: ${sortedList.take(3)}")
        return sortedList.take(3)
    }

    private fun getLastThreeExpenditures(cashEntryList: List<CashEntry>): List<CashEntry> {
        val sortedList = cashEntryList.filter { cashEntry ->
            cashEntry.isIncome == true
        }.sortedByDescending {
            it.transactionDate
        }

        Log.d("TAGM", "getLastThreeExpenditures: ${sortedList.take(3)}")
       return sortedList.take(3)
    }
}