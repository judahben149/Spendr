package com.judahben149.spendr.presentation.add_cash_entry

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentAddCashEntryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddCashEntryFragment : Fragment() {

    private var _binding: FragmentAddCashEntryBinding? = null
    val binding get() = _binding!!

    private val viewModel: AddCashEntryViewModel by viewModels()
    private var isIncome: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddCashEntryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.state.observe(viewLifecycleOwner) { state ->

            binding.etAmount.setText(state.amount.toString())
            isIncome = state.isIncome
        }

        binding.btnSave.setOnClickListener {
            if (!binding.etAmount.text.isNullOrEmpty()) {
                viewModel.updateAmount(binding.etAmount.text.toString().toDouble())
            } else {
                viewModel.updateAmount(0.00)
            }
            viewModel.saveEntry()
        }
    }

    override fun onPause() {
        super.onPause()
        if (!binding.etAmount.text.isNullOrEmpty()) {
            viewModel.updateAmount(binding.etAmount.text.toString().toDouble())
        }
    }
}