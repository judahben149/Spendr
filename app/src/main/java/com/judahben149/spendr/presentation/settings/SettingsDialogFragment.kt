package com.judahben149.spendr.presentation.settings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.DialogSettingsBinding
import com.judahben149.spendr.utils.Constants.DIALOG_TYPE
import com.judahben149.spendr.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsDialogFragment: DialogFragment() {

    private var _binding: DialogSettingsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: SettingsViewModel by viewModels()

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

        val dialogType = arguments?.getString(DIALOG_TYPE) ?: ""

        when (dialogType) {
            "entries" -> {
                binding.tvDialogHeader.text = getString(R.string.tv_delete_entries_header)
                binding.tvDialogBody.text = getString(R.string.tv_delete_entries_body)

                binding.btnNegative.setOnClickListener {
                    dismiss()
                }
                binding.btnPositive.setOnClickListener {
                    viewModel.deleteAllEntries()
                    dismiss()
                }
            }

            "reminders" -> {
                binding.tvDialogHeader.text = getString(R.string.tv_delete_reminders_header)
                binding.tvDialogBody.text = getString(R.string.tv_delete_reminders_body)
                binding.tvPositiveBtn.text = getString(R.string.delete_all_reminders)
                binding.tvNegativeButton.text = getString(R.string.delete_expired_reminders)

                binding.btnNegative.setOnClickListener {
                    viewModel.deleteExpiredReminders(DateUtils.getCurrentDateInMillis())
                    dismiss()
                }
                binding.btnPositive.setOnClickListener {
                    viewModel.deleteAllReminders()
                    dismiss()
                }
            }
            else -> {}
        }
    }

    companion object {

        fun newInstance(dialogType: String): SettingsDialogFragment {
            val args = Bundle().apply {
                putString(DIALOG_TYPE, dialogType)
            }
            val fragment = SettingsDialogFragment()
            fragment.arguments = args
            return fragment
        }
    }
}