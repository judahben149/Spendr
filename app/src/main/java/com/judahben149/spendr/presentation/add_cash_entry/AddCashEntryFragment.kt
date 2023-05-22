package com.judahben149.spendr.presentation.add_cash_entry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentAddCashEntryBinding
import com.judahben149.spendr.utils.Constants.DATE_PICKER_ADD_CASH_ENTRY
import com.judahben149.spendr.utils.DateUtils.formatFriendlyDateTime
import com.judahben149.spendr.utils.DateUtils.getCurrentDateInMillis
import com.judahben149.spendr.utils.extensions.highlight
import com.judahben149.spendr.utils.extensions.unHighlight
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddCashEntryFragment : Fragment() {

    private var _binding: FragmentAddCashEntryBinding? = null
    val binding get() = _binding!!

    private val viewModel: AddCashEntryViewModel by viewModels()
    private var isIncome: Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCashEntryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTodayDate()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.etAmount.setText(state.amount.toString())
            isIncome = state.isIncome

            displayFriendlyDate(state.date)
        }

        viewModel.categoryState.observe(viewLifecycleOwner) { categoryState ->
            if (categoryState.isIncomeSelected) {
                binding.btnIncome.highlight()
                binding.btnExpenditure.unHighlight()
            } else {
                binding.btnExpenditure.highlight()
                binding.btnIncome.unHighlight()
            }
        }

        binding.btnSave.setOnClickListener {
            if (!binding.etAmount.text.isNullOrEmpty()) {
                viewModel.updateAmount(binding.etAmount.text.toString().toDouble())
            } else {
                viewModel.updateAmount(0.00)
            }
            viewModel.saveEntry()
        }

        binding.layoutBtnIncome.setOnClickListener {
            viewModel.setCashEntryType(isIncome = true)
        }

        binding.layoutBtnExpenditure.setOnClickListener {
            viewModel.setCashEntryType(isIncome = false)
        }

        binding.tvDate.setOnClickListener {
            datePicker()
        }
    }

    private fun setTodayDate() {
        val currentDate = getCurrentDateInMillis()
        viewModel.setCurrentDate(currentDate)
    }

    private fun displayFriendlyDate(dateLong: Long) {
        binding.tvDate.text = formatFriendlyDateTime(dateLong)
    }

    private fun datePicker() {

        val datePicker: MaterialDatePicker<Long> =
            MaterialDatePicker.Builder.datePicker().setTitleText("Choose Date").build()
        datePicker.show(parentFragmentManager, DATE_PICKER_ADD_CASH_ENTRY)

        datePicker.addOnPositiveButtonClickListener { dateLong ->
            viewModel.setCurrentDate(dateLong)
        }
    }

    override fun onPause() {
        super.onPause()
        if (!binding.etAmount.text.isNullOrEmpty()) {
            viewModel.updateAmount(binding.etAmount.text.toString().toDouble())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}