package com.judahben149.spendr.presentation.entry_list.epoxy.model

import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelEntryListMonthHeaderBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class EntryListMonthHeaderEpoxyModel(
    val monthName: String
): ViewBindingKotlinModel<EpoxyModelEntryListMonthHeaderBinding>(R.layout.epoxy_model_entry_list_month_header) {

    override fun EpoxyModelEntryListMonthHeaderBinding.bind() {
        tvHeaderMonthName.text = monthName
    }
}
