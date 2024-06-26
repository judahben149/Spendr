package com.judahben149.spendr.presentation.add_cash_entry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.judahben149.spendr.databinding.FragmentAddCashEntryBinding
import com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.BottomSheetContainerFragment
import com.judahben149.spendr.utils.Constants.CATEGORY_BOTTOM_SHEET
import com.judahben149.spendr.utils.Constants.DATE_PICKER_ADD_CASH_ENTRY
import com.judahben149.spendr.utils.DateUtils.formatFriendlyDateTime
import com.judahben149.spendr.utils.DateUtils.getCurrentDateInMillis
import com.judahben149.spendr.utils.TextUtils
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import com.judahben149.spendr.utils.extensions.highlight
import com.judahben149.spendr.utils.extensions.mapCategoryIcon
import com.judahben149.spendr.utils.extensions.unHighlight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCashEntryFragment : Fragment() {

    private var _binding: FragmentAddCashEntryBinding? = null
    val binding get() = _binding!!

    private var textChangedByListener = true
    private val viewModel: AddCashEntryViewModel by activityViewModels()
    private var isIncome: Boolean = false

    val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCashEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        viewModel.reset()
        setTodayDate()

        binding.tvReason.hint = TextUtils.reasonRandomizer()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            if (!textChangedByListener) {
                binding.etAmount.setText(if (state.amount == 0) "" else state.amount.toString())
            } else {
                textChangedByListener = false
            }
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

        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            binding.ivSelectedCategoryIcon.mapCategoryIcon(category.categoryIconId)
            binding.tvCategory.text = category.categoryName
        }


        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                textChangedByListener = true
                viewModel.updateAmount(
                    if (editable.isNullOrEmpty() || !editable.toString().isDigitsOnly()) 0 else editable.toString().toInt()
                )
            }
        })

        binding.btnSave.setOnClickListener {
            viewModel.updateReason(binding.tvReason.text?.toString() ?: "")
            viewModel.saveEntry()
            navController.popBackStack()
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
        binding.tvCategory.setOnClickListener {
            BottomSheetContainerFragment().show(childFragmentManager, CATEGORY_BOTTOM_SHEET)
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
            viewModel.updateAmount(binding.etAmount.text.toString().toInt())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}