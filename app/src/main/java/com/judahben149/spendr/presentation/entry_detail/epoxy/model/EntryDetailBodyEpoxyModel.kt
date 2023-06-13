package com.judahben149.spendr.presentation.entry_detail.epoxy.model

import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelEntryDetailBodyBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.DateUtils

@Keep
data class EntryDetailBodyEpoxyModel(
    val cashEntry: CashEntry
): ViewBindingKotlinModel<EpoxyModelEntryDetailBodyBinding>(R.layout.epoxy_model_entry_detail_body) {

    override fun EpoxyModelEntryDetailBodyBinding.bind() {

        tvDate.text = DateUtils.formatFriendlyDateTime(cashEntry.transactionDate)
        tvCategory.text = cashEntry.categoryName
        tvTime.text = DateUtils.getTimeOfDayFromDateInMillis(cashEntry.transactionDate)
    }
}
