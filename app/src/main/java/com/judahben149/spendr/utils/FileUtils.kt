package com.judahben149.spendr.utils

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.core.content.FileProvider
import com.google.android.material.snackbar.Snackbar
import java.io.File

object FileUtils {

    fun openPdfFile(context: Context, incomingFile: File, onError:(message: String) -> Unit) {
        val file = File(incomingFile.path)
        val fileUri: Uri =
            FileProvider.getUriForFile(context, "com.judahben149.spendr.fileProvider", file)

        val viewIntent = Intent(Intent.ACTION_VIEW)
        viewIntent.setDataAndType(fileUri, "application/pdf")
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(viewIntent)
        } catch (e: ActivityNotFoundException) {
            onError("PDF Viewer not available")
        }
    }

    fun locatePdfFile(context: Context, incomingFile: File, onError:(message: String) -> Unit) {
        val file = File(incomingFile.path)
        val fileUri: Uri =
            FileProvider.getUriForFile(context, "com.judahben149.spendr.fileProvider", file)

        val intent = Intent(Intent.ACTION_VIEW)
        intent.setDataAndType(fileUri, "resource/folder")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK

        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            onError("File Manager not available")
        }
    }
}