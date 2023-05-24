package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryIncomeTitleBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

data class SummaryIncomeHeaderEpoxyModel(
    val incomeTitle: String
): ViewBindingKotlinModel<EpoxyModelSummaryIncomeTitleBinding>(R.layout.epoxy_model_summary_income_title) {

    override fun EpoxyModelSummaryIncomeTitleBinding.bind() {
        tvIncomeHeaderTitle.text = incomeTitle
    }
}
