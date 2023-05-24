package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import android.widget.LinearLayout
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSpacerBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

data class SummarySpacer(
    val spacerHeight: Int
): ViewBindingKotlinModel<EpoxyModelSpacerBinding>(R.layout.epoxy_model_spacer) {

    override fun EpoxyModelSpacerBinding.bind() {

    }
}
