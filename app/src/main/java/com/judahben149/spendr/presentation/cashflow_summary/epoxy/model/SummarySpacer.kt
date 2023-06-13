package com.judahben149.spendr.presentation.cashflow_summary.epoxy.model

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.Dimension
import androidx.annotation.Dimension.Companion.PX
import androidx.annotation.Keep
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.EpoxyModelSpacerBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.ViewBindingKotlinModel

@Keep
data class SummarySpacer(
    @Dimension(unit = PX) val spacerHeight: Int
): ViewBindingKotlinModel<EpoxyModelSpacerBinding>(R.layout.epoxy_model_spacer) {

    override fun EpoxyModelSpacerBinding.bind() {
//        root.layoutParams = ViewGroup.LayoutParams(
//            ViewGroup.LayoutParams.MATCH_PARENT,
//            spacerHeight
//        )
    }
}
