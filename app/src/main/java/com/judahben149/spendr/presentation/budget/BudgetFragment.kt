package com.judahben149.spendr.presentation.budget

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentBudgetBinding
import com.judahben149.spendr.databinding.FragmentExtrasBinding
import com.judahben149.spendr.utils.extensions.animateToolBarTitle

class BudgetFragment : Fragment() {

    private var _binding: FragmentBudgetBinding? = null
    val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBudgetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.neuCardVisualizer.setOnClickListener {
            navController.navigate(R.id.action_budgetFragment_to_visualizerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}