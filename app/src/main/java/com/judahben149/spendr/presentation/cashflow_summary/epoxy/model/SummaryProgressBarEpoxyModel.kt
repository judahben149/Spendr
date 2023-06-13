package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import android.view.View
import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelProgressBarBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummaryProgressBarEpoxyModel(
    val nothing: String? = null
): ViewBindingKotlinModel<EpoxyModelProgressBarBinding>(R.layout.epoxy_model_progress_bar) {

    override fun EpoxyModelProgressBarBinding.bind() {
        pgBarSummary.visibility = View.VISIBLE
    }
}
