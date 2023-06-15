package com.judahben149.spendr.utils

import android.content.Context
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.kernel.pdf.CompressionConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.property.TextAlignment
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream

object PDFHelper {

    fun generatePDF(
        context: Context,
        rootView: View,
        cashEntry: CashEntry,
        onFinish: (file: File) -> Unit,
        onError: (exception: Exception) -> Unit
    ) {

        val file = createFile(context, cashEntry)

        try {
            val fileOutput = FileOutputStream(file)
            val pdfWriter = PdfWriter(fileOutput)
            pdfWriter.compressionLevel = CompressionConstants.BEST_COMPRESSION

            val pdfDocument = PdfDocument(pdfWriter)
            val layoutDocument = Document(pdfDocument)

            addTitle(layoutDocument, "Spendr Entry Detail")
            addEmptyLine(layoutDocument, 2)
            addLineLeftFontSize18(layoutDocument, "Amount: ${cashEntry.amount.abbreviateNumber()}")
            addLineLeftFontSize18(layoutDocument, "Category: ${cashEntry.categoryName}")
            addLineLeftFontSize18(layoutDocument, "Date: ${DateUtils.formatStandardDateTime(cashEntry.transactionDate)}")
            addLineLeftFontSize18(layoutDocument, "Entry Type: ${if (cashEntry.isIncome) "Income" else "Expenditure"}")

            layoutDocument.close()
            Snackbar.make(rootView, "File saved to ${file.path}", Snackbar.LENGTH_INDEFINITE).show()
        }
        catch (exception: Exception) {
            onError(exception)
        }
        finally {
            onFinish(file)
        }
    }

    private fun createFile(context: Context, cashEntry: CashEntry): File {
        val fileName = DateUtils.formatPdfFileName(cashEntry.transactionDate)
        val filePath = getAppPath(context) + fileName
        val file = File(filePath)

        if (file.exists()) {
            file.delete()
        }
        return file
    }

    private fun getAppPath(context: Context): String {
        val dir = context.getExternalFilesDir(null)
        val spendrDir = File(dir, "PDFs")

        if (!spendrDir.exists()) {
            if (spendrDir.mkdirs()) {
                Timber.tag(TIMBER_TAG).d("Directory created successfully")
            } else {
                Timber.tag(TIMBER_TAG).d("Failed to create directory")
            }
        }

        Timber.tag(TIMBER_TAG).d(spendrDir.path + File.separator)
        return spendrDir.path + File.separator
    }


    private fun addTitle(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setFontSize(24f).setBold().setUnderline()
                .setTextAlignment(TextAlignment.CENTER)
        )
    }

    private fun addEmptyLine(layoutDocument: Document, number: Int) {
        for (i in 0 until number) {
            layoutDocument.add(Paragraph(" "))
        }
    }

    private fun addLineLeftFontSize18(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setFontSize(16f)
                .setTextAlignment(TextAlignment.LEFT)
        )
    }
}
