package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import com.google.errorprone.annotations.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryTitleBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummaryHeaderEpoxyModel(
    val headerTitle: String
): ViewBindingKotlinModel<EpoxyModelSummaryTitleBinding>(R.layout.epoxy_model_summary_title) {

    override fun EpoxyModelSummaryTitleBinding.bind() {
        tvItemSummaryTitle.text = headerTitle
    }
}
