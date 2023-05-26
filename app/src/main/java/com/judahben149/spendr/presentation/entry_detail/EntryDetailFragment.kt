package com.judahben149.spendr.presentation.entry_detail

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.airbnb.epoxy.EpoxyRecyclerView
import com.google.android.material.snackbar.Snackbar
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCashFlowSummaryBinding
import com.judahben149.spendr.databinding.FragmentEntryDetailBinding
import com.judahben149.spendr.presentation.entry_detail.epoxy.EntryDetailEpoxyController
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.DummyData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryDetailFragment : Fragment() {

    private var _binding: FragmentEntryDetailBinding? = null
    val binding get() = _binding!!

    private val viewModel: EntryDetailViewModel by viewModels()
    private lateinit var entryDetailEpoxyController: EntryDetailEpoxyController
    private lateinit var epoxyRecyclerView: EpoxyRecyclerView

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
        setupEpoxyController()

        Log.d("TAGM", "onViewCreated: $entryId")
        entryId?.let { id ->
            viewModel.getEntryDetail(id)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.cashEntry?.let {
                entryDetailEpoxyController.setData(it, DummyData.categoryMap)
            }
        }


    }

    private fun setupEpoxyController() {
        epoxyRecyclerView = binding.epoxyRvEntryDetail
        entryDetailEpoxyController = EntryDetailEpoxyController()

        epoxyRecyclerView.setController(entryDetailEpoxyController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}