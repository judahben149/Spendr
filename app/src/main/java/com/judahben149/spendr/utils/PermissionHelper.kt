package com.judahben149.spendr.utils

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.content.ContextCompat
import com.judahben149.spendr.presentation.MainActivity
import com.judahben149.spendr.presentation.SMS_REQUEST_CODE
import com.judahben149.spendr.presentation.settings.PermissionResultCallback
import javax.inject.Inject

class PermissionHelper @Inject constructor(private val context: Context) {
//    fun setPermissionCallback(callback: PermissionResultCallback) {
//        this.callback = callback
//    }

    fun isSmsPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.RECEIVE_SMS
        ) == PackageManager.PERMISSION_GRANTED
    }


    fun requestNeededPermissions(activity: MainActivity, callback: PermissionResultCallback) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && activity.checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {

            callback.let {
                activity.setPermissionResultCallBack(it)
            }

            activity.requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE)
        } else {

        }
    }
}