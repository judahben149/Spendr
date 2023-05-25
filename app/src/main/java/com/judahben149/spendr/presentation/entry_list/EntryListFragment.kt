package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.databinding.FragmentEntryDetailBinding
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

    var isIncomeEntryType: Boolean? = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showEntryType()
        setupEpoxyController()

        lifecycleScope.launch {
            viewModel.pagedCashEntries.collectLatest { pagedCashEntries ->
                var entries: PagingData<CashEntry> = pagedCashEntries

                //Because this fragment is reused across both income and expenditure
                // calling sites, filter for which to display
                isIncomeEntryType?.let { isIncomeEntryType ->
                    if (isIncomeEntryType) {
                        val incomeEntries = pagedCashEntries.filter { cashEntry ->
                            cashEntry.isIncome
                        }
                        entries = incomeEntries
                    } else {
                        val expenditureEntries = pagedCashEntries.filter { cashEntry ->
                            !cashEntry.isIncome
                        }
                        entries = expenditureEntries
                    }
                }
                entryListController.submitData(entries)
            }
        }

    }

    private fun setupEpoxyController() {
        entryListRv = binding.epoxyRvEntryList

        entryListController = EntryListController(onEntryItemClicked = {
            Toast.makeText(requireContext(), "You clicked me", Toast.LENGTH_SHORT).show()
        })

        entryListRv.setController(entryListController)
    }

    private fun showEntryType() {
        isIncomeEntryType = arguments?.getBoolean(Constants.ENTRY_TYPE)

        isIncomeEntryType?.let {
            Snackbar.make(binding.root, if (it) "Income" else "Expenditure", Snackbar.LENGTH_SHORT)
                .show()
        }
    }
}