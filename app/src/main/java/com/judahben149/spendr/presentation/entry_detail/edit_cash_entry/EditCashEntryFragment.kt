package com.judahben149.spendr.presentation.entry_detail.edit_cash_entry

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.judahben149.spendr.databinding.FragmentAddCashEntryBinding
import com.judahben149.spendr.databinding.FragmentEditCashEntryBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.presentation.add_cash_entry.AddCashEntryViewModel
import com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.BottomSheetContainerFragment
import com.judahben149.spendr.presentation.entry_detail.EntryDetailViewModel
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import com.judahben149.spendr.utils.extensions.highlight
import com.judahben149.spendr.utils.extensions.mapCategoryIcon
import com.judahben149.spendr.utils.extensions.unHighlight
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditCashEntryFragment : Fragment() {

    private var _binding: FragmentEditCashEntryBinding? = null
    val binding get() = _binding!!

    private var textChangedByListener = true
    private val viewModel: AddCashEntryViewModel by activityViewModels()
    private val detailViewModel: EntryDetailViewModel by viewModels()
    private var isIncome: Boolean = false

    val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditCashEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        viewModel.reset()

        var entryId = 0

        arguments?.getInt(Constants.ENTRY_DETAIL_ID)?.let { id ->
            entryId = id
            detailViewModel.getEntryDetail(id)
        }

        prefillViews()


        viewModel.state.observe(viewLifecycleOwner) { state ->
            displayFriendlyDate(state.date)
            isIncome = state.isIncome
        }

        viewModel.selectedCategory.observe(viewLifecycleOwner) { category ->
            binding.ivSelectedCategoryIcon.mapCategoryIcon(category.categoryIconId)
            binding.tvCategory.text = category.categoryName
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

        binding.run {
            btnSave.setOnClickListener {

                val cashEntry = CashEntry(
                    id = entryId,
                    amount = etAmount.text.toString().toDouble(),
                    isIncome = isIncome,
                    categoryId = viewModel.state.value!!.categoryId,
                    transactionDate = viewModel.state.value!!.date,
                    categoryIconId = viewModel.state.value!!.categoryIconId,
                    categoryName = viewModel.state.value!!.categoryName,
                    isIncomeCategory = viewModel.state.value!!.isIncomeCategory,
                    reason = etReason.text?.toString() ?: ""
                )

                viewModel.updateCashEntry(cashEntry)
                navController.popBackStack()
            }
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
            BottomSheetContainerFragment().show(
                childFragmentManager,
                Constants.CATEGORY_BOTTOM_SHEET
            )
        }
    }

    private fun prefillViews() {

        detailViewModel.state.observe(viewLifecycleOwner) { state ->
            state.cashEntry?.let { entry ->

                binding.run {
                    etAmount.setText(entry.amount.toString())

                    if (entry.isIncome) {
                        btnIncome.highlight()
                        btnExpenditure.unHighlight()
                        viewModel.setCashEntryType(true)
                        isIncome = true
                    } else {
                        btnExpenditure.highlight()
                        btnIncome.unHighlight()
                        viewModel.setCashEntryType(false)
                        isIncome = false
                    }

                    val category = Category(
                        categoryId = entry.categoryId,
                        categoryName = entry.categoryName,
                        categoryIconId = entry.categoryIconId,
                        isIncomeCategory = entry.isIncomeCategory
                    )

                    viewModel.updateSelectedCategoryId(category)

//                    ivSelectedCategoryIcon.mapCategoryIcon(entry.categoryIconId)
//                    tvCategory.text = entry.categoryName

                    etReason.setText(entry.reason)

                    viewModel.setCurrentDate(entry.transactionDate)
                }
            }
        }
    }


    private fun displayFriendlyDate(dateLong: Long) {
        binding.tvDate.text = DateUtils.formatFriendlyDateTime(dateLong)
    }

    private fun datePicker() {

        val datePicker: MaterialDatePicker<Long> =
            MaterialDatePicker.Builder.datePicker().setTitleText("Choose Date").build()
        datePicker.show(parentFragmentManager, Constants.DATE_PICKER_ADD_CASH_ENTRY)

        datePicker.addOnPositiveButtonClickListener { dateLong ->
            viewModel.setCurrentDate(dateLong)
        }
    }


//    override fun onPause() {
//        super.onPause()
//        if (!binding.etAmount.text.isNullOrEmpty()) {
//            viewModel.updateAmount(binding.etAmount.text.toString().toInt())
//        }
//    }
}