package com.judahben149.spendr.presentation.components

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.DialogSettingsBinding
import com.judahben149.spendr.presentation.entry_detail.EntryDetailViewModel
import com.judahben149.spendr.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReusableCustomDialog: DialogFragment() {

    private var _binding: DialogSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: EntryDetailViewModel by activityViewModels()

    var callBack: ReusableCustomDialogCallBack? = null

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dialogTitle = arguments?.getString(Constants.DIALOG_TITLE) ?: ""
        val dialogBody = arguments?.getString(Constants.DIALOG_BODY) ?: ""
        val dialogPositiveBtn = arguments?.getString(Constants.DIALOG_POSITIVE_BUTTON) ?: "Yes"
        val dialogNegativeBtn = arguments?.getString(Constants.DIALOG_NEGATIVE_BUTTON) ?: "No"
        val enterTextMode = arguments?.getBoolean(Constants.ENTER_TEXT_MODE) ?: false
        val requestCode = arguments?.getInt(Constants.DIALOG_REQUEST_CODE) ?: 0

        binding.tvDialogHeader.text = dialogTitle
        binding.tvDialogBody.text = dialogBody
        binding.tvPositiveBtn.text = dialogPositiveBtn
        binding.tvNegativeButton.text = dialogNegativeBtn

        if (enterTextMode) {
            binding.tvDialogBody.visibility = View.INVISIBLE
            binding.etTextField.visibility = View.VISIBLE

            binding.etTextField.requestFocus()
            dialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        }

        binding.btnNegative.setOnClickListener {
            callBack?.onNegativeAction(requestCode)
            dismiss()
        }

        binding.btnPositive.setOnClickListener {
            callBack?.onPositiveAction(requestCode, binding.etTextField.text?.toString() ?: "")
            dismiss()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        fun newInstance(
            callBack: ReusableCustomDialogCallBack,
            dialogTitle: String,
            dialogBody: String,
            dialogPositiveBtn: String,
            dialogNegativeBtn: String,
            enterTextMode: Boolean = false,
            requestCode: Int = 0
        ): ReusableCustomDialog {

            val args = Bundle().apply {
                putString(Constants.DIALOG_TITLE, dialogTitle)
                putString(Constants.DIALOG_BODY, dialogBody)
                putString(Constants.DIALOG_POSITIVE_BUTTON, dialogPositiveBtn)
                putString(Constants.DIALOG_NEGATIVE_BUTTON, dialogNegativeBtn)
                putBoolean(Constants.ENTER_TEXT_MODE, enterTextMode)
                putInt(Constants.DIALOG_REQUEST_CODE, requestCode)
            }

            val fragment = ReusableCustomDialog()
            fragment.arguments = args
            fragment.callBack = callBack

            return fragment
        }
    }
}