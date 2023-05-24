package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryEntryCardBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

data class SummaryEntryItemEpoxyModel(
    val amount: String,
    val date: String,
    val category: String,
): ViewBindingKotlinModel<EpoxyModelSummaryEntryCardBinding>(R.layout.epoxy_model_summary_entry_card) {

    override fun EpoxyModelSummaryEntryCardBinding.bind() {
        tvEntryAmount.text = amount
        tvEntryCategory.text = category
        tvEntryDate.text = date

        imageCategoryIcon.mapCategoryIcon(category)
    }
}
