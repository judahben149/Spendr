package com.judahben149.spendr.presentation.entry_detail

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.judahben149.spendr.databinding.DialogBottomShareSheetBinding
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.PDFGenerator
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import dagger.hilt.android.AndroidEntryPoint
import java.lang.StringBuilder

@AndroidEntryPoint
class ShareSheetBottomDialog: BottomSheetDialogFragment() {

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
                .append("Date: ".plus(DateUtils.formatStandardDateTime(entry.transactionDate)).plus("\n"))
                .append("Date: ".plus(DateUtils.getTimeOfDayFromDateInMillis(entry.transactionDate)).plus("\n"))
                .append("Category: ".plus(entry.categoryName).plus("\n"))
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
                PDFGenerator.generatePDF(requireContext(), binding.root, it)
                dismiss()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}