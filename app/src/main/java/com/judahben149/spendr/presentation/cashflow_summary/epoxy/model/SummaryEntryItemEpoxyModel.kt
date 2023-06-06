package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import android.content.Context
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryEntryCardBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

data class SummaryEntryItemEpoxyModel(
    val context: Context,
    val cashEntry: CashEntry,
    val onEntryItemClicked:() -> Unit
): ViewBindingKotlinModel<EpoxyModelSummaryEntryCardBinding>(R.layout.epoxy_model_summary_entry_card) {

    override fun EpoxyModelSummaryEntryCardBinding.bind() {
        tvEntryAmount.text = cashEntry.amount.abbreviateNumber(context)
        tvEntryCategory.text = cashEntry.categoryName
        tvEntryDate.text = DateUtils.formatFriendlyDateTime(cashEntry.transactionDate)

        imageCategoryIcon.mapCategoryIcon(cashEntry.categoryIconId)

        cardEntry.setOnClickListener {
            onEntryItemClicked()
        }
    }
}
