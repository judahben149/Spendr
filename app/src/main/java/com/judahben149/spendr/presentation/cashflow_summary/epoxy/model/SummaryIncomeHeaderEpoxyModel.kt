package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryIncomeTitleBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummaryIncomeHeaderEpoxyModel(
    val incomeTitle: String,
    val onSeeAllIncomeClicked:() -> Unit
): ViewBindingKotlinModel<EpoxyModelSummaryIncomeTitleBinding>(R.layout.epoxy_model_summary_income_title) {

    override fun EpoxyModelSummaryIncomeTitleBinding.bind() {
        tvIncomeHeaderTitle.text = incomeTitle

        tvIncomeHeaderText.setOnClickListener {
            onSeeAllIncomeClicked()
        }
    }
}
