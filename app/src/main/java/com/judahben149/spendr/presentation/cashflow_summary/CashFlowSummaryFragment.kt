package com.judahben149.spendr.presentation.cashflow_summary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.SummaryEpoxyController
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CashFlowSummaryFragment : Fragment() {

    private var _binding: FragmentCashFlowSummaryBinding? = null
    val binding get() = _binding!!

    private val viewModel: CashFlowSummaryViewModel by viewModels()
    val navController by lazy {
        findNavController()
    }

    private val summaryEpoxyController: SummaryEpoxyController by lazy {
        SummaryEpoxyController(
            onSeeAllIncomeClicked = {
                handleNavigationEvent(CashFlowSummaryNavigationDestinations.EntryListFragment(true))
            }, onSeeAllExpenditureClicked = {
                handleNavigationEvent(CashFlowSummaryNavigationDestinations.EntryListFragment(false))
            }, onEntryItemClicked = { entryId ->
                handleNavigationEvent(CashFlowSummaryNavigationDestinations.EntryDetailFragment(entryId))
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCashFlowSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener { navController.popBackStack() }

        viewModel.getCashEntries()
        binding.epoxyRvSummaryScreen.setController(summaryEpoxyController)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            summaryEpoxyController.setData(state)
        }
    }

    private fun handleNavigationEvent(destination: CashFlowSummaryNavigationDestinations) {
        when(destination) {
            is CashFlowSummaryNavigationDestinations.EntryListFragment -> {
                val isIncomeEntryTypeBundle = destination.isIncomeEntryType
                val bundle = Bundle().apply {
                    putBoolean(Constants.IS_INCOME_ENTRY_TYPE, isIncomeEntryTypeBundle)
                    putBoolean(Constants.SHOULD_SHOW_TOOL_BAR, true)
                }
                navController.navigate(R.id.entryListFragment, bundle)
            }

            is CashFlowSummaryNavigationDestinations.EntryDetailFragment -> {
                val bundle = Bundle().apply {
                    putInt(Constants.ENTRY_ID, destination.entryId)
                }
                navController.navigate(R.id.entryDetailFragment, bundle)
            }
        }
    }
}

sealed class CashFlowSummaryNavigationDestinations() {
    data class EntryListFragment(val isIncomeEntryType: Boolean): CashFlowSummaryNavigationDestinations()
    data class EntryDetailFragment(val entryId: Int): CashFlowSummaryNavigationDestinations()
}