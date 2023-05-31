package com.judahben149.spendr.presentation.entry_detail.epoxy.model

import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelEntryDetailBodyBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.DateUtils

data class EntryDetailBodyEpoxyModel(
    val cashEntry: CashEntry,
    val categoryMap: Map<Int, String>
): ViewBindingKotlinModel<EpoxyModelEntryDetailBodyBinding>(R.layout.epoxy_model_entry_detail_body) {

    override fun EpoxyModelEntryDetailBodyBinding.bind() {
        val categoryName = categoryMap[cashEntry.categoryId] ?: "Uncategorized"

        tvDate.text = DateUtils.formatFriendlyDateTime(cashEntry.transactionDate)
        tvCategory.text = categoryName
        tvTime.text = DateUtils.getTimeOfDayFromDateInMillis(cashEntry.transactionDate)
    }
}
