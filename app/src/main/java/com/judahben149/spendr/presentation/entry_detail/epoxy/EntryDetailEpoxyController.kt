package com.judahben149.spendr.presentation.entry_detail.epoxy

import android.content.Context
import android.util.Log
import com.airbnb.epoxy.Typed2EpoxyController
import com.airbnb.epoxy.TypedEpoxyController
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.entry_detail.epoxy.model.EntryDetailBodyEpoxyModel
import com.judahben149.spendr.presentation.entry_detail.epoxy.model.EntryDetailHeaderEpoxyModel

class EntryDetailEpoxyController(private val context: Context): TypedEpoxyController<CashEntry>() {


    override fun buildModels(cashEntry: CashEntry?) {

        cashEntry?.let {  entry ->
                EntryDetailHeaderEpoxyModel(context, entry).id("entryDetail_header").addTo(this)
                EntryDetailBodyEpoxyModel(entry).id("entryDetail_body").addTo(this)
        }
    }
}