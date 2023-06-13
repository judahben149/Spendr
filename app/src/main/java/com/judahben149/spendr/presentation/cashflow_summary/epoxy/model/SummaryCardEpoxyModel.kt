package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryNeumorphicCardBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummaryCardEpoxyModel(
    val incomeAmount: String,
    val expenditureAmount: String,
    val month: String
): ViewBindingKotlinModel<EpoxyModelSummaryNeumorphicCardBinding>(R.layout.epoxy_model_summary_neumorphic_card) {

    override fun EpoxyModelSummaryNeumorphicCardBinding.bind() {
        tvInflowAmount.text = incomeAmount
        tvOutflowAmount.text = expenditureAmount
        tvMonthName.text = month

    }
}
