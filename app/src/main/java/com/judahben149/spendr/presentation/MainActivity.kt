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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        requestNeededPermissions()
    }

    override fun onResume() {
        super.onResume()
        if (sessionManager.canReceiveSmsEntries() && !permissionHelper.isSmsPermissionGranted()) {
            requestNeededPermissions()
        }
    }

    private fun requestNeededPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECEIVE_SMS), SMS_REQUEST_CODE)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == SMS_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showToast(applicationContext, "Sms Permission Granted")
            } else {
                showToast(applicationContext, "Sms Permission NOT Granted")
            }
        }
    }


    private fun showPermissionInfoDialog() {
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Grant SMS Permission")
        builder.setMessage("SMS Permission is required to create entries from alerts")

        builder.setPositiveButton("Grant"){ dialog, which ->
            // Request permission to receive sms
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, applicationContext.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            applicationContext.startActivity(intent)
        }

        builder.setCancelable(false)
        builder.setNegativeButton("Ignore") { dialog, which ->
            sessionManager.toggleSmsEntryFunctionality(false)
            dialog.dismiss()
        }

        builder.create().show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}