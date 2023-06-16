package com.judahben149.spendr.presentation.settings

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.DialogExportBudgetBinding
import com.judahben149.spendr.utils.FileUtils
import com.judahben149.spendr.utils.PDFHelper
import dagger.hilt.android.AndroidEntryPoint
import java.io.File

@AndroidEntryPoint
class ExportBudgetDialogFragment : DialogFragment() {

    private var _binding: DialogExportBudgetBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ExportBudgetViewModel by viewModels()

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogExportBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getAllBudgetEntries()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (state.isExportComplete) {
                if (state.budgetEntries.isNotEmpty()) {
                    PDFHelper.generateEntireBudgetTable(
                        requireContext(),
                        state.budgetEntries,
                        onFinish = {
                            setExportCompleteView(it)
                        },
                        onError = {

                        })
                } else {
                    setEmptyBudgetView()
                }
            }
        }
    }

    private fun setExportCompleteView(file: File) {
        binding.btnPositive.visibility = View.VISIBLE
        binding.btnNegative.visibility = View.VISIBLE
        binding.tvDialogBody.visibility = View.VISIBLE
        binding.pgBarExport.visibility = View.INVISIBLE
        binding.tvDialogHeader.text = "Export complete"

        binding.btnNegative.setOnClickListener {
            FileUtils.locatePdfFile(requireContext(), file, onError = {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            })
            dismiss()
        }
        binding.btnPositive.setOnClickListener {
            FileUtils.openPdfFile(requireContext(), file, onError = {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_LONG).show()
            })
            dismiss()
        }
    }

    private fun setEmptyBudgetView() {
        binding.tvDialogBody.visibility = View.VISIBLE
        binding.pgBarExport.visibility = View.INVISIBLE

        binding.tvDialogHeader.text = "Export not complete"
        binding.tvDialogBody.text = "Your Spendr Budget is empty."
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}