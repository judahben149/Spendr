package com.judahben149.spendr.presentation.entry_detail.epoxy.model

import android.content.Context
import androidx.annotation.Keep
import androidx.core.content.ContextCompat
import com.judahben149.spendr.R
import com.judahben149.spendr.data.local.entity.CategoryEntity
import com.judahben149.spendr.databinding.EpoxyModelEntryDetailHeaderBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

@Keep
data class EntryDetailHeaderEpoxyModel(
    val context: Context,
    val cashEntry: CashEntry
): ViewBindingKotlinModel<EpoxyModelEntryDetailHeaderBinding>(R.layout.epoxy_model_entry_detail_header) {

    override fun EpoxyModelEntryDetailHeaderBinding.bind() {

        tvAmountEntryDetails.text = cashEntry.amount.abbreviateNumber(context)
        tvTitleEntryDetails.text = cashEntry.categoryName
        ivCategoryIcon.mapCategoryIcon(cashEntry.categoryIconId)
        if (cashEntry.isIncome) {
            animEntryDetail.setAnimation("ic_up_trendline.json")
        } else {
            animEntryDetail.setAnimation("ic_down_trendline.json")
        }
    }
}
