package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryExpenditureTitleBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

data class SummaryExpenditureHeaderEpoxyModel(
    val expenditureHeader: String
): ViewBindingKotlinModel<EpoxyModelSummaryExpenditureTitleBinding>(R.layout.epoxy_model_summary_expenditure_title) {

    override fun EpoxyModelSummaryExpenditureTitleBinding.bind() {
        tvExpenditureHeaderTitle.text = expenditureHeader
    }
}
