package com.judahben149.spendr.presentation.cashflow_summary

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.databinding.FragmentHomeBinding
import com.judahben149.spendr.presentation.cashflow_summary.epoxy.SummaryEpoxyController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CashFlowSummaryFragment : Fragment() {

    private var _binding: FragmentCashFlowSummaryBinding? = null
    val binding get() = _binding!!

    private val viewModel: CashFlowSummaryViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCashFlowSummaryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getCashEntries()

        val summaryEpoxyController = SummaryEpoxyController()
        binding.epoxyRvSummaryScreen.setController(summaryEpoxyController)

        viewModel.state.observe(viewLifecycleOwner) { state ->
            summaryEpoxyController.setData(state.cashEntryList)
        }
    }
}