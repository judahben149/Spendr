package com.judahben149.spendr.presentation.entry_detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentEntryDetailBinding
import com.judahben149.spendr.presentation.components.ReusableCustomDialog
import com.judahben149.spendr.presentation.components.ReusableCustomDialogCallBack
import com.judahben149.spendr.presentation.entry_detail.epoxy.EntryDetailEpoxyController
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.DELETE_ENTRY_DIALOG
import com.judahben149.spendr.utils.Constants.SHARE_SHEET_BOTTOM_DIALOG
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryDetailFragment : Fragment(), ReusableCustomDialogCallBack {

    private var _binding: FragmentEntryDetailBinding? = null
    val binding get() = _binding!!

    private val viewModel: EntryDetailViewModel by activityViewModels()
    private lateinit var entryDetailEpoxyController: EntryDetailEpoxyController
    private lateinit var epoxyRecyclerView: EpoxyRecyclerView
    val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener { navController.popBackStack() }
        viewModel.reset()

        val entryId = arguments?.getInt(Constants.ENTRY_ID)
        setupEpoxyController()

        entryId?.let { id ->
            viewModel.getEntryDetail(id)
        }

        viewModel.state.observe(viewLifecycleOwner) { state ->
            state.cashEntry?.let {
                    entryDetailEpoxyController.setData(state.cashEntry)
            }
        }

        binding.run {
            btnShare.setOnClickListener {
                ShareSheetBottomDialog().show(childFragmentManager, SHARE_SHEET_BOTTOM_DIALOG)
            }

            btnDelete.setOnClickListener {
                val deleteDialog = ReusableCustomDialog.newInstance(
                    this@EntryDetailFragment,
                    "Delete Budget Entry?",
                    "This entry will be deleted. Click \"Yes\" to proceed.",
                    "Yes",
                    "No"
                )

                deleteDialog.show(childFragmentManager, DELETE_ENTRY_DIALOG)
            }

            btnEdit.setOnClickListener {
                navigateToEditScreen()
            }
        }
    }

    private fun navigateToEditScreen() {
        val entryId = viewModel.getEntryId()

        entryId?.let { id ->
            val bundle = Bundle().apply {
                putInt(Constants.ENTRY_DETAIL_ID, id)
            }

            navController.navigate(R.id.editCashEntryFragment, bundle)
        }
    }

    private fun setupEpoxyController() {
        epoxyRecyclerView = binding.epoxyRvEntryDetail
        entryDetailEpoxyController = EntryDetailEpoxyController(
            requireContext()
        )

        epoxyRecyclerView.setController(entryDetailEpoxyController)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onPositiveAction(requestCode: Int, text: String) {
        navController.popBackStack()
        viewModel.deleteEntry()
    }

    override fun onNegativeAction(requestCode: Int) {

    }
}