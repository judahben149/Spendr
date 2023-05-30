package com.judahben149.spendr.presentation.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentHomeBinding
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    val navController by lazy {
        findNavController()
    }
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getBalance()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            showProgressBar(state.isBalanceLoading)
            binding.tvInflowAmount.text = state.inflowBalance.abbreviateNumber()
            binding.tvOutflowAmount.text = state.outflowBalance.abbreviateNumber()
        }

        binding.itemSummaryNeumorphicCard.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_cashFlowSummaryFragment)
        }
        binding.cardTransactions.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_entryListParentFragment)
        }
        binding.cardVisualizer.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_visualizerFragment)
        }
        binding.cardAddEntry.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_addCashEntryFragment)
        }
    }

    private fun showProgressBar(isLoading: Boolean) {
        if (isLoading) {
            binding.tvInflowAmount.visibility = View.INVISIBLE
            binding.tvInflowText.visibility = View.INVISIBLE
            binding.tvOutflowAmount.visibility = View.INVISIBLE
            binding.tvOutflowText.visibility = View.INVISIBLE
            binding.pgBarInflowAmount.visibility = View.VISIBLE
            binding.pgBarOutflowAmount.visibility = View.VISIBLE
        } else {
            binding.tvInflowAmount.visibility = View.VISIBLE
            binding.tvInflowText.visibility = View.VISIBLE
            binding.tvOutflowAmount.visibility = View.VISIBLE
            binding.tvOutflowText.visibility = View.VISIBLE
            binding.pgBarInflowAmount.visibility = View.INVISIBLE
            binding.pgBarOutflowAmount.visibility = View.INVISIBLE
        }
    }
}