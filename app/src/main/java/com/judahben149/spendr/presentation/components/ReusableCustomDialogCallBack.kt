package com.judahben149.spendr.presentation.components

interface ReusableCustomDialogCallBack {

    fun onPositiveAction(requestCode: Int, text: String)

    fun onNegativeAction(requestCode: Int)
}