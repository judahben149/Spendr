package com.judahben149.spendr.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.View
import androidx.annotation.DrawableRes
import com.google.android.material.snackbar.Snackbar
import com.itextpdf.io.image.ImageData
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.CompressionConstants
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import com.itextpdf.layout.property.UnitValue
import com.judahben149.spendr.R
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import timber.log.Timber
import java.io.ByteArrayOutputStream
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

        val file = createSingleEntryFile(context, cashEntry)

        try {
            val fileOutput = FileOutputStream(file)
            val pdfWriter = PdfWriter(fileOutput)
            pdfWriter.compressionLevel = CompressionConstants.BEST_COMPRESSION

            val pdfDocument = PdfDocument(pdfWriter)
            val layoutDocument = Document(pdfDocument)

            addLogo(context, layoutDocument, R.drawable.splash_piggy_pdf, "Spendr")
            addTitle(layoutDocument, "Spendr Entry Detail")
            addEmptyLine(layoutDocument, 2)

            addLineLeftFontSize18(
                layoutDocument,
                "Amount: ${cashEntry.amount.abbreviateNumber(context)}"
            )

            addLineLeftFontSize18(layoutDocument, "Category: ${cashEntry.categoryName}")
            addLineLeftFontSize18(layoutDocument, "Reason: ${cashEntry.reason}")

            addLineLeftFontSize18(
                layoutDocument,
                "Date: ${DateUtils.formatStandardDateTime(cashEntry.transactionDate)}"
            )

            addLineLeftFontSize18(
                layoutDocument,
                "Entry Type: ${if (cashEntry.isIncome) "Income" else "Expenditure"}"
            )

            layoutDocument.close()
            Snackbar.make(rootView, "File saved to ${file.path}", Snackbar.LENGTH_INDEFINITE).show()
        } catch (exception: Exception) {
            onError(exception)
        } finally {
            onFinish(file)
        }
    }

    private fun createSingleEntryFile(context: Context, cashEntry: CashEntry): File {
        val fileName = DateUtils.formatPdfFileName(cashEntry.transactionDate) + ".pdf"
        val filePath = getAppPath(context, true) + fileName
        val file = File(filePath)

        if (file.exists()) {
            file.delete()
        }
        return file
    }

    private fun createBudgetFile(context: Context): File {
        val currentDate = DateUtils.getCurrentDateInMillis()
        val fileName = DateUtils.formatPdfFileName(currentDate) + ".pdf"
        val filePath = getAppPath(context, false) + fileName
        val file = File(filePath)

        if (file.exists()) {
            file.delete()
        }
        return file
    }

    private fun getAppPath(context: Context, isSingleEntry: Boolean): String {
        val dir = context.getExternalFilesDir(null)
        val spendrDir = if (isSingleEntry) File(dir, "Single") else File(dir, "Entire")

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

    private fun addLogo(
        context: Context,
        layoutDocument: Document,
        @DrawableRes logoImageDrawableId: Int,
        logoText: String
    ) {
        val logoImage = createItextImage(context, logoImageDrawableId, 24F, 24F)

        layoutDocument.add(logoImage).add(
            Paragraph("   $logoText").setFontSize(6f).setBold()
                .setTextAlignment(TextAlignment.LEFT)
        )
    }

    private fun createItextImage(
        context: Context,
        @DrawableRes imageDrawableId: Int,
        imageHeight: Float,
        imageWidth: Float,
    ): Image {
        val logoImageDrawable = context.getDrawable(imageDrawableId)
        val logoImageBitmap = (logoImageDrawable as BitmapDrawable).bitmap
        val byteArrayOutput = ByteArrayOutputStream()

        logoImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutput)
        val bitmapData: ByteArray = byteArrayOutput.toByteArray()

        val imageData: ImageData = ImageDataFactory.create(bitmapData)
        val image: Image = Image(imageData)
        image.setHeight(imageHeight)
        image.setWidth(imageWidth)

        return image
    }

    private fun addTitle(layoutDocument: Document, text: String) {
        layoutDocument.add(
            Paragraph(text).setFontSize(18f).setBold().setUnderline()
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

    private fun addLine(layoutDocument: Document, text: String, fontSize: Float) {
        layoutDocument.add(
            Paragraph(text).setFontSize(fontSize)
                .setTextAlignment(TextAlignment.LEFT)
        )
    }

    fun generateEntireBudgetTable(
        context: Context,
        cashEntryList: List<CashEntry>,
        onFinish: (file: File) -> Unit,
        onError: (exception: Exception) -> Unit
    ) {
        val file = createBudgetFile(context)

        try {
            val fileOutput = FileOutputStream(file)
            val pdfWriter = PdfWriter(fileOutput)
            pdfWriter.compressionLevel = CompressionConstants.BEST_COMPRESSION

            val pdfDocument = PdfDocument(pdfWriter)
            val layoutDocument = Document(pdfDocument)

            addLogo(context, layoutDocument, R.drawable.splash_piggy_pdf, "Spendr")
            addTitle(layoutDocument, "Spendr Budget Export")
            addLine(
                layoutDocument,
                "Date exported - ${DateUtils.getCurrentFriendlyDateWithTime()}",
                10f
            )
            addEmptyLine(layoutDocument, 1)

            val table = Table(
                UnitValue.createPointArray(
                    floatArrayOf(
                        100f,
                        80f,
                        80f,
                        100f,
                        120f
                    )
                )
            )

            table.addCell(
                Paragraph("Date").setBold().setFontSize(10f).setTextAlignment(TextAlignment.CENTER)
            )
            table.addCell(
                Paragraph("Amount").setBold().setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
            )
            table.addCell(
                Paragraph("Entry Type").setBold().setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
            )
            table.addCell(
                Paragraph("Category").setBold().setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
            )
            table.addCell(
                Paragraph("Reason").setBold().setFontSize(10f)
                    .setTextAlignment(TextAlignment.CENTER)
            )

            var monthName = ""
            var totalIncome = 0.0
            var totalExpenditure = 0.0

            for (entry in cashEntryList) {

                if (entry.isIncome)
                    totalIncome += entry.amount
                else
                    totalExpenditure += entry.amount


                table.addCell(
                    Paragraph(DateUtils.formatStandardDateTime(entry.transactionDate) + "")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                table.addCell(
                    Paragraph(entry.amount.abbreviateNumber(context) + "")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                table.addCell(
                    Paragraph(if (entry.isIncome) "Income" else "Expenditure" + "")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                table.addCell(
                    Paragraph(entry.categoryName + "")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
                table.addCell(
                    Paragraph(entry.reason + "")
                        .setFontSize(10f)
                        .setTextAlignment(TextAlignment.CENTER)
                )
            }

            val totalExpenseTable = Table(
                UnitValue.createPointArray(
                    floatArrayOf(
                        80f,
                        80f,
                        80f,
                        100f,
                        120f
                    )
                )
            )

            totalExpenseTable.addCell(
                Paragraph("Total Income:").setBold().setTextAlignment(TextAlignment.CENTER)
            )
            totalExpenseTable.addCell(
                Paragraph(totalIncome.abbreviateNumber(context)).setTextAlignment(
                    TextAlignment.CENTER
                )
            )
            totalExpenseTable.addCell(
                Paragraph("Total Expenditure").setBold().setTextAlignment(TextAlignment.CENTER)
            )
            totalExpenseTable.addCell(
                Paragraph(totalExpenditure.abbreviateNumber(context)).setTextAlignment(
                    TextAlignment.CENTER
                )
            )


            layoutDocument.add(table)
            addEmptyLine(layoutDocument, 1)
            layoutDocument.add(totalExpenseTable)

            layoutDocument.close()
        } catch (exception: Exception) {
            onError(exception)
        } finally {
            onFinish(file)
        }
    }
}
