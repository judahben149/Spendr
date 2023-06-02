package com.judahben149.spendr.presentation.extras.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.judahben149.spendr.databinding.FragmentReminderDetailsBottomSheetBinding
import com.judahben149.spendr.domain.model.Reminder
import com.judahben149.spendr.utils.DateUtils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReminderDetailsBottomSheetFragment(): Fragment() {

    private var _binding: FragmentReminderDetailsBottomSheetBinding? = null
    val binding get() = _binding!!

    private var subjectTextChangedByListener = true
    private var amountTextChangedByListener = true

    private val viewModel: RemindersViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentReminderDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        setEditTextListeners()

        viewModel.newReminderState.observe(viewLifecycleOwner) { state ->
            //observe values entered from viewModel and set them
            binding.etReminderAmount.setText(state.amount.toString())
            binding.etReminderText.setText(state.reminderText)
            binding.tvReminderDate.text = DateUtils.formatFriendlyDateTime(state.targetDate)
            setEditTextValues(state)
        }

        binding.swtchIsRecurrent.setOnCheckedChangeListener { compoundButton, isChecked ->
            viewModel.setIsRecurrent(isChecked)
        }

        binding.tvSaveOrUpdate.setOnClickListener {
            val amount = if (binding.etReminderAmount.text.isNullOrEmpty()) 0.0 else binding.etReminderAmount.text.toString().toDouble()
            val subject = if (binding.etReminderText.text.isNullOrEmpty()) "" else binding.etReminderText.text.toString()
            viewModel.saveReminder(amount, subject)
        }
    }


    private fun setEditTextValues(state: Reminder?) {
        if (!subjectTextChangedByListener) {
            binding.etReminderText.setText(state?.reminderText)
        } else {
            subjectTextChangedByListener = false
        }

        if (!amountTextChangedByListener) {
            binding.etReminderAmount.setText(if (state?.amount == 0.0) "" else state?.amount.toString())
        } else {
            amountTextChangedByListener = false
        }
    }

//    private fun setEditTextListeners() {
//        binding.etReminderAmount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun afterTextChanged(editable: Editable?) {
//                amountTextChangedByListener = true
//                viewModel.setAmount(
//                    if (editable.isNullOrEmpty()) 0.0 else editable.toString().toDouble()
//                )
//            }
//        })
//
//        binding.etReminderText.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {}
//
//            override fun afterTextChanged(editable: Editable?) {
//                subjectTextChangedByListener = true
//                viewModel.setReminderText(
//                    if (editable.isNullOrEmpty()) "" else editable.toString()
//                )
//            }
//        })
//    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}