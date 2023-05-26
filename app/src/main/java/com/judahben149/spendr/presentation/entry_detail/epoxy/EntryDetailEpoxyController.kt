package com.judahben149.spendr.presentation.entry_detail.epoxy

import android.util.Log
import com.airbnb.epoxy.Typed2EpoxyController
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.entry_detail.epoxy.model.EntryDetailHeaderEpoxyModel

class EntryDetailEpoxyController: Typed2EpoxyController<CashEntry, Map<Int, String>>() {


    override fun buildModels(cashEntry: CashEntry?, categoryMap: Map<Int, String>?) {

        cashEntry?.let {  entry ->
            categoryMap?.let {  map ->
                Log.d("TAGM", "buildModels: ${entry.toString()} \n ${map.toString()}")
                EntryDetailHeaderEpoxyModel(entry, map).id("entryDetail_header").addTo(this)
            }
        }
    }

}