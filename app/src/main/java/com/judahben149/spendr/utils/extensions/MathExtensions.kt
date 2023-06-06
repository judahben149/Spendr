package com.judahben149.spendr.utils.extensions

import android.content.Context
import com.judahben149.spendr.utils.CurrencyUtils
import java.text.NumberFormat

fun Double.abbreviateNumber(context: Context): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 0
        val formattedNumber = numberFormat.format(this)

        val currencySymbol = CurrencyUtils().getCurrencySymbol(context)

        return if (formattedNumber.get(0).toString() == "-") {
                val stringBuilder = StringBuilder(formattedNumber)
                stringBuilder.insert(1, currencySymbol).toString()
        } else {
                val stringBuilder = StringBuilder(formattedNumber)
                stringBuilder.insert(0, currencySymbol).toString()
        }
}

fun Double.abbreviateNumber(): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(this)
}