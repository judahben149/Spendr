package com.judahben149.spendr.utils.extensions

import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import timber.log.Timber

fun Any.log(message: String) {
    Timber.tag(TIMBER_TAG).d(message)
}