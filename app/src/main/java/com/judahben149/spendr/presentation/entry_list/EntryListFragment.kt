package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.filter
import com.airbnb.epoxy.EpoxyRecyclerView
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentEntryListBinding
import com.judahben149.spendr.domain.model.CashEntry
import com.judahben149.spendr.presentation.entry_list.epoxy.EntryListController
import com.judahben149.spendr.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class EntryListFragment : Fragment() {

    private var _binding: FragmentEntryListBinding? = null
    val binding get() = _binding!!

    private val viewModel: EntryListViewModel by viewModels()
    private lateinit var entryListController: EntryListController
    private lateinit var entryListRv: EpoxyRecyclerView

    val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEntryType()

        binding.toolBar.setOnClickListener {
            navController.popBackStack()
        }
        setupEpoxyController()

        lifecycleScope.launch {
            viewModel.pagedCashEntries.collectLatest { pagedCashEntries ->
                var entries: PagingData<CashEntry> = pagedCashEntries

                //Because this fragment is reused to show both income and expenditure
                // entries, filter for which to display
                when(viewModel.state.value!!.entryListType) {
                    is EntryListType.IncomeEntry -> {
                        val incomeEntries = pagedCashEntries.filter { cashEntry ->
                            cashEntry.isIncome
                        }
                        entries = incomeEntries
                    }

                    is EntryListType.ExpenditureEntry -> {
                        val expenditureEntries = pagedCashEntries.filter { cashEntry ->
                            !cashEntry.isIncome
                        }
                        entries = expenditureEntries
                    }

                    is EntryListType.AllEntry -> {
                        entries = pagedCashEntries
                    }
                }
                entryListController.submitData(entries)
            }
        }
    }

    private fun showOrHideToolBar(title: String) {
        val showToolBar = arguments?.getBoolean(Constants.SHOULD_SHOW_TOOL_BAR)

        showToolBar?.let { shouldShowToolBar ->
            if (shouldShowToolBar) {
                binding.tvToolbarTitle.text = title
                return
            }
        }

        binding.toolBar.visibility = View.GONE
    }


    private fun setEntryType() {
        val isIncomeEntryType = arguments?.getBoolean(Constants.IS_INCOME_ENTRY_TYPE)

        isIncomeEntryType?.let {
            if (isIncomeEntryType) {
                showOrHideToolBar("Income")
                viewModel.updateEntryListType(EntryListType.IncomeEntry)
            } else if (!isIncomeEntryType) {
                showOrHideToolBar("Expenditure")
                viewModel.updateEntryListType(EntryListType.ExpenditureEntry)
            }
        }
    }

    private fun setupEpoxyController() {
        entryListRv = binding.epoxyRvEntryList

        entryListController = EntryListController(onEntryItemClicked = { itemId ->

            handleItemClicks(itemId)
        })
        entryListRv.setController(entryListController)
    }

    private fun handleItemClicks(itemId: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.ENTRY_ID, itemId)
        }

        navController.navigate(R.id.entryDetailFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}