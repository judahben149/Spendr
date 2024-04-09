package com.judahben149.spendr.presentation

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.judahben149.spendr.databinding.ActivityMainBinding
import com.judahben149.spendr.presentation.settings.PermissionResultCallback
import com.judahben149.spendr.utils.PermissionHelper
import com.judahben149.spendr.utils.SessionManager
import com.judahben149.spendr.utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

const val SMS_REQUEST_CODE = 10000

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    val binding get() = _binding!!

    @Inject
    lateinit var permissionHelper: PermissionHelper

    @Inject
    lateinit var sessionManager: SessionManager

    private var callback: PermissionResultCallback? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                callback?.onSmsPermissionGranted()
            } else {
                callback?.onSmsPermissionDenied()
            }
        }
    }

    fun setPermissionResultCallBack(callback: PermissionResultCallback) {
        this.callback = callback
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}