package com.judahben149.spendr.utils.extensions

import timber.log.Timber

fun Any.log(tag: String) {
    Timber.tag(tag).d(this.toString())
}