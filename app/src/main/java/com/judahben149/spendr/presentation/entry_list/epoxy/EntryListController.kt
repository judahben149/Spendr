package com.judahben149.spendr.presentation.entry_list.epoxy

import android.util.Log
import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryEntryItemEpoxyModel
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber

class EntryListController(
    private val onEntryItemClicked:() -> Unit
): PagingDataEpoxyController<CashEntry>() {

    override fun buildItemModel(currentPosition: Int, entry: CashEntry?): EpoxyModel<*> {

        Log.d("TAGM", "getALlPagedCashEntries: controller")
        return SummaryEntryItemEpoxyModel(
            amount = entry?.amount?.abbreviateNumber()!!,
            date = DateUtils.formatFriendlyDateTime(entry?.transactionDate!!),
            category = "Savings",
            onEntryItemClicked = { onEntryItemClicked() }
        ).id("pagedCashEntry")
    }
}