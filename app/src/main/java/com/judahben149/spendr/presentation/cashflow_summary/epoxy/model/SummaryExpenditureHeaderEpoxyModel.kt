package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import android.util.Log
import android.widget.Toast
import androidx.annotation.Keep
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSummaryExpenditureTitleBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummaryExpenditureHeaderEpoxyModel(
    val expenditureHeader: String,
    val onSeeAllExpenditureClicked:() -> Unit
): ViewBindingKotlinModel<EpoxyModelSummaryExpenditureTitleBinding>(R.layout.epoxy_model_summary_expenditure_title) {

    override fun EpoxyModelSummaryExpenditureTitleBinding.bind() {
        tvExpenditureHeaderTitle.text = expenditureHeader

        tvExpenditureHeaderText.setOnClickListener {
            onSeeAllExpenditureClicked()
        }
    }
}
