package com.judahben149.spendr.utils

import android.content.Context
import android.os.Environment
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import com.judahben149.spendr.R
import com.judahben149.spendr.domain.model.CashEntry
import java.io.File
import java.io.FileOutputStream

object PDFGenerator {

    fun generatePDF(context: Context, rootView: View, cashEntry: CashEntry) {
        val fileName = DateUtils.formatPdfFileName(cashEntry.transactionDate)
        val filePath = getAppPath(context) + fileName

        if (File(filePath).exists()) {
            File(filePath).delete()
        }

        val fileOutput = FileOutputStream(filePath)
        val pdfWriter = PdfWriter(fileOutput)

        val pdfDocument = PdfDocument(pdfWriter)
        val layoutDocument = Document(pdfDocument)

        addTitle(layoutDocument, "Spendr Entry Detail")

        layoutDocument.close()
        Snackbar.make(rootView, "File saved to $filePath", Snackbar.LENGTH_INDEFINITE).show()
    }

    private fun getAppPath(context: Context): String {
        val dir = File(
            Environment.getExternalStorageDirectory().toString()
                    + File.separator
                    + context.resources.getString(R.string.app_name)
                    + File.separator
        )

        if (!dir.exists()) {
            dir.mkdir()
        }
        return dir.path + File.separator
    }

    private fun addTitle(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setBold().setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
        )
    }

    private fun addEmptyLine(layoutDocument: Document, number: Int) {
        for (i in 0 until number) {
            layoutDocument.add(Paragraph(" "))
        }
    }
}