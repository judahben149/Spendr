package com.judahben149.spendr.presentation.entry_detail.epoxy.model

import com.judahben149.spendr.R
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.databinding.EpoxyModelEntryDetailHeaderBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

data class EntryDetailHeaderEpoxyModel(
    val cashEntry: CashEntry
): ViewBindingKotlinModel<EpoxyModelEntryDetailHeaderBinding>(R.layout.epoxy_model_entry_detail_header) {

    override fun EpoxyModelEntryDetailHeaderBinding.bind() {

        tvAmountEntryDetails.text = cashEntry.amount.abbreviateNumber()
        tvTitleEntryDetails.text = cashEntry.categoryName
        ivCategoryIcon.mapCategoryIcon(cashEntry.categoryIconId)
    }
}
