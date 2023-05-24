package com.judahben149.spendr.presentation.entry_detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.databinding.FragmentEntryDetailBinding
import com.judahben149.spendr.utils.Constants

class EntryDetailFragment : Fragment() {

    private var _binding: FragmentEntryDetailBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entryId = arguments?.getInt(Constants.ENTRY_ID)

        entryId?.let {
            Snackbar.make(binding.root, "ID is $entryId", Snackbar.LENGTH_SHORT).show()
        }
    }
}