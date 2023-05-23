package com.judahben149.spendr.utils.extensions

import java.text.NumberFormat

fun Double.abbreviateNumber(): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 0
        return numberFormat.format(this)
}