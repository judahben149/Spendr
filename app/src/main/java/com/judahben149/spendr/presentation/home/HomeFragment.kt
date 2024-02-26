package com.judahben149.spendr.presentation.home

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentHomeBinding
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.SessionManager
import com.judahben149.spendr.utils.extensions.abbreviateNumber
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    val binding get() = _binding!!

    val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var appPrefs: SharedPreferences

    @Inject
    lateinit var sessionManager: SessionManager

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

        if (sessionManager.isFirstLaunch())
            navController.navigate(R.id.action_homeFragment_to_onboardingFragment)

        binding.tvToolbarTitle.setText("Hi, ".plus(sessionManager.getUserName()))
        viewModel.getBalance()

        binding.tvDate.text = DateUtils.getCurrentFriendlyDate()

        viewModel.state.observe(viewLifecycleOwner) { state ->
            binding.tvAmountBalance.text = if (state.isBalanceHidden) {
                "*****"
            } else {
                state.inflowBalance.minus(state.outflowBalance).abbreviateNumber(requireContext())
            }

            binding.tvAmountBalance.setTextColor(
                if (state.inflowBalance.minus(state.outflowBalance) < 0.0) resources.getColor(
                    R.color.syracuse_red_orange
                ) else resources.getColor(R.color.pigment_green)
            )

            toggleBalanceVisibility(state.isBalanceHidden)
        }

        binding.run {
            itemSummaryNeumorphicCard.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_cashFlowSummaryFragment)
            }

            cardTransactions.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_entryListParentFragment)
            }

            cardBudget.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_budgetFragment)
            }

            cardAddEntry.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_addCashEntryFragment)
            }

            cardExtras.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_extrasFragment)
            }

            cardSettings.setOnClickListener {
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
            }

            binding.btnBalanceVisibility.setOnClickListener {
                viewModel.toggleBalanceVisibility()
            }
        }
    }

    private fun toggleBalanceVisibility(shouldHide: Boolean) {
        if (shouldHide) {
            binding.animEyeOpened.visibility = View.INVISIBLE
            binding.animEyeClosed.visibility = View.VISIBLE
        } else {
            binding.animEyeOpened.visibility = View.VISIBLE
            binding.animEyeClosed.visibility = View.INVISIBLE
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}