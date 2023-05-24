package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.databinding.FragmentEntryDetailBinding
import com.judahben149.spendr.databinding.FragmentEntryListBinding
import com.judahben149.spendr.utils.Constants

class EntryListFragment : Fragment() {

    private var _binding: FragmentEntryListBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val isIncomeEntryType = arguments?.getBoolean(Constants.ENTRY_TYPE)

        isIncomeEntryType?.let {
            Snackbar.make(binding.root, if (it) "Income" else "Expenditure", Snackbar.LENGTH_SHORT).show()
        }
    }
}