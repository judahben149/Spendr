package com.judahben149.spendr.presentation.extras.reminders

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.judahben149.spendr.databinding.FragmentReminderDetailsBottomSheetBinding
import com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.BottomSheetContainerFragment
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ReminderDetailsBottomSheetFragment(): Fragment() {

    private var _binding: FragmentReminderDetailsBottomSheetBinding? = null
    val binding get() = _binding!!

    private val viewModel: RemindersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTomorrowDate()

        viewModel.newReminderState.observe(viewLifecycleOwner) { state ->
            //observe values entered from viewModel and set them
            binding.tvReminderDate.text = DateUtils.formatFriendlyDateTime(state.targetDate)
            binding.tvReminderTime.text = DateUtils.getFriendlyTime(state.targetDate)
        }

        binding.tvReminderDate.setOnClickListener {
            datePicker()
        }

        binding.tvReminderTime.setOnClickListener {
            val currentDate = viewModel.newReminderState.value?.targetDate
            if (currentDate != null) {
                timePicker(currentDate)
            }
        }

        binding.swtchIsRecurrent.setOnCheckedChangeListener { compoundButton, isChecked ->
            viewModel.setIsRecurrent(isChecked)
        }

        binding.tvSaveOrUpdate.setOnClickListener {
            val amount = if (binding.etReminderAmount.text.isNullOrEmpty()) 0.0 else binding.etReminderAmount.text.toString().toDouble()
            val subject = if (binding.etReminderText.text.isNullOrEmpty()) "" else binding.etReminderText.text.toString()
            viewModel.saveReminder(amount, subject)
            dismissBottomSheet()
        }
    }

    private fun datePicker() {

        val datePicker: MaterialDatePicker<Long> =
            MaterialDatePicker.Builder.datePicker().setTitleText("Choose Date").build()
        datePicker.show(parentFragmentManager, Constants.DATE_PICKER_ADD_CASH_ENTRY)

        datePicker.addOnPositiveButtonClickListener { dateLong ->
            Toast.makeText(requireContext(), dateLong.toString(), Toast.LENGTH_SHORT).show()
            viewModel.setTargetDate(dateLong)
        }
    }

    private fun timePicker(targetDate: Long) {

        val timePicker: MaterialTimePicker =
            MaterialTimePicker.Builder().setTitleText("Choose Time").build()
        timePicker.show(parentFragmentManager, Constants.DATE_PICKER_ADD_CASH_ENTRY)

        timePicker.addOnPositiveButtonClickListener {
            val selectedHour = timePicker.hour
            val selectedMinute = timePicker.minute

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = targetDate
            calendar.set(Calendar.HOUR_OF_DAY, selectedHour)
            calendar.set(Calendar.MINUTE, selectedMinute)

            val timeInMillis = calendar.timeInMillis

            viewModel.setTargetDate(timeInMillis)
        }
    }

    private fun dismissBottomSheet() {
        val parentBottomSheet = parentFragment?.parentFragment as RemindersContainerBottomSheet
        parentBottomSheet.dismiss()
    }

    private fun setTomorrowDate() {
        val currentDate = DateUtils.getTomorrowDateInMillis()
        viewModel.setTargetDate(currentDate)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}