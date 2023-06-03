package com.judahben149.spendr.presentation.extras.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.NOTIFICATION_BUNDLE
import com.judahben149.spendr.utils.Constants.REMINDER_TEXT
import com.judahben149.spendr.utils.Constants.REMINDER_TITLE
import com.judahben149.spendr.utils.DrawableUtils
import com.judahben149.spendr.utils.NotificationHelper

class RemindersReceiver(): BroadcastReceiver() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onReceive(context: Context, intent: Intent?) {
        val notificationHelper = NotificationHelper(context)
        val notificationTitle = intent?.getBundleExtra(NOTIFICATION_BUNDLE)?.getString(REMINDER_TITLE)
        val notificationContent = intent?.getBundleExtra(NOTIFICATION_BUNDLE)?.getString(REMINDER_TEXT)
        val notificationId = 17

        notificationHelper.showNotification(notificationTitle ?: "", notificationContent ?: "", notificationId)
    }
}