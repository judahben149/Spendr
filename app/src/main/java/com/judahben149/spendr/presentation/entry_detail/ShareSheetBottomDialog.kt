package com.judahben149.spendr.presentation.entry_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.FileProvider
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.databinding.DialogBottomShareSheetBinding
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.PDFHelper
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.lang.StringBuilder
import java.util.logging.FileHandler

@AndroidEntryPoint
class ShareSheetBottomDialog : BottomSheetDialogFragment() {

    private var _binding: DialogBottomShareSheetBinding? = null
    private val binding get() = _binding!!
    private val viewModel: EntryDetailViewModel by activityViewModels()

    private var formattedText = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogBottomShareSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cashEntry = viewModel.state.value?.cashEntry

        cashEntry?.let { entry ->
            formattedText = StringBuilder()
                .append("Amount: ".plus(entry.amount.abbreviateNumber(requireContext())).plus("\n"))
                .append(
                    "Date: ".plus(DateUtils.formatStandardDateTime(entry.transactionDate))
                        .plus("\n")
                )
                .append(
                    "Date: ".plus(DateUtils.getTimeOfDayFromDateInMillis(entry.transactionDate))
                        .plus("\n")
                )
                .append("Category: ".plus(entry.categoryName).plus("\n"))
                .append("Reason: ".plus(entry.reason).plus("\n"))
                .append("\n")
                .append("With love, from Spendr.")
                .toString()
        }

        binding.btnText.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, formattedText)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, "Spendr Entry Details")
            startActivity(shareIntent)

            dismiss()
        }

        binding.btnPdf.setOnClickListener {
            cashEntry?.let {
                PDFHelper.generatePDF(
                    context = requireContext(),
                    rootView = binding.root,
                    cashEntry = it,
                    onFinish = { file ->
                        openFile(requireContext(), file)
                    },
                    onError = { exception ->
                        Snackbar.make(binding.root, "Error - $exception", Snackbar.LENGTH_LONG).show()
                    }
                )
                dismiss()
            }
        }
    }

    private fun openFile(context: Context, incomingFile: File) {
        val file = File(incomingFile.path)
        val fileUri: Uri = FileProvider.getUriForFile(context, "com.judahben149.spendr.fileProvider", file)

        val viewIntent = Intent(Intent.ACTION_VIEW)
        viewIntent.setDataAndType(fileUri, "application/pdf")
        viewIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        viewIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

        try {
            context.startActivity(viewIntent)
        } catch (e: ActivityNotFoundException) {
            Snackbar.make(binding.root, "PDF Viewer not available", Snackbar.LENGTH_LONG).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}