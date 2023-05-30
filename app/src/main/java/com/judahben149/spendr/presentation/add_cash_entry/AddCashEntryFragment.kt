package com.judahben149.spendr.presentation.add_cash_entry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowId
import android.view.WindowId.FocusObserver
import android.widget.ListAdapter
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.datepicker.MaterialDatePicker
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentAddCashEntryBinding
import com.judahben149.spendr.presentation.add_cash_entry.adapter.CategoryAdapter
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.DATE_PICKER_ADD_CASH_ENTRY
import com.judahben149.spendr.utils.DateUtils.formatFriendlyDateTime
import com.judahben149.spendr.utils.DateUtils.getCurrentDateInMillis
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import com.judahben149.spendr.utils.extensions.highlight
import com.judahben149.spendr.utils.extensions.unHighlight
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.Locale

@AndroidEntryPoint
class AddCashEntryFragment : Fragment() {

    private var _binding: FragmentAddCashEntryBinding? = null
    val binding get() = _binding!!

    private var textChangedByListener = true
    private val viewModel: AddCashEntryViewModel by viewModels()
    private var isIncome: Boolean = false

    lateinit var adapter: CategoryAdapter
    lateinit var categoryRecyclerView: RecyclerView
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

        setTodayDate()
        setupCategoryAdapter()
        viewModel.getCategories()

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
            showProgressBar(categoryState.isLoading)

            //observe for changes in selected category. Uses the map function to map the category with the id in question
            viewModel.selectedCategoryState.observe(viewLifecycleOwner) { selectedCategoryState ->
                adapter.updateSelectedCategoryState(selectedCategoryState)

                if (categoryState.isIncomeSelected) {
                    binding.btnIncome.highlight()
                    binding.btnExpenditure.unHighlight()
                    adapter.submitList(categoryState.categoryList?.filter { category ->
                        category.isIncomeCategory
                    })
                } else {
                    binding.btnExpenditure.highlight()
                    binding.btnIncome.unHighlight()
                    adapter.submitList(categoryState.categoryList?.filter { category ->
                        !category.isIncomeCategory
                    })
                }
            }
        }

        binding.etAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(editable: Editable?) {
                textChangedByListener = true
                viewModel.updateAmount(
                    if (editable.isNullOrEmpty()) 0 else editable.toString().toInt()
                )
            }
        })

        binding.btnSave.setOnClickListener {
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
    }

    private fun setTodayDate() {
        val currentDate = getCurrentDateInMillis()
        viewModel.setCurrentDate(currentDate)
    }

    private fun setupCategoryAdapter() {
        categoryRecyclerView = binding.rvCategory
        adapter = CategoryAdapter() { selectedId ->
            viewModel.updateSelectedCategoryId(selectedId)
        }

        categoryRecyclerView.adapter = adapter
        categoryRecyclerView.layoutManager = GridLayoutManager(
            requireContext(),
            3,
            LinearLayoutManager.HORIZONTAL,
            false
        )
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

    fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.pgBarCategory.visibility = View.VISIBLE
        } else {
            binding.pgBarCategory.visibility = View.INVISIBLE
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