package com.judahben149.spendr.presentation.entry_list.epoxy

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.judahben149.spendr.domain.model.EntryListData
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.model.SummaryEntryItemEpoxyModel
import com.judahben149.spendr.presentation.entry_list.epoxy.model.EntryListMonthHeaderEpoxyModel
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import timber.log.Timber

class EntryListController(
    private val onEntryItemClicked: (id: Int) -> Unit
) : PagingDataEpoxyController<EntryListData>() {

    override fun buildItemModel(currentPosition: Int, item: EntryListData?): EpoxyModel<*> {

        return when (item) {
            is EntryListData.EntryItem -> {
                SummaryEntryItemEpoxyModel(
                    cashEntry = item.entryItem,
                    onEntryItemClicked = { onEntryItemClicked(item.entryItem.id) }
                ).id("pagedCashEntry")
            }

            is EntryListData.MonthHeader -> {
                EntryListMonthHeaderEpoxyModel(
                    monthName = item.month
                ).id("monthHeader")
            }

            else -> error("unsupported model type")
        }
    }

    override fun isStickyHeader(position: Int): Boolean {
        val item = adapter.getModelAtPosition(position)
        Timber.tag(TIMBER_TAG).d("is sticky header called")

        if (item is EntryListMonthHeaderEpoxyModel) {
            Timber.tag(TIMBER_TAG).d("is sticky header called in IF statement")
            return true
        }
        return false
    }
}