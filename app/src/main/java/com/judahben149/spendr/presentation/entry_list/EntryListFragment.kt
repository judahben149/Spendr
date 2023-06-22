package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.filter
import androidx.paging.map
import com.airbnb.epoxy.EpoxyRecyclerView
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentEntryListBinding
import com.judahben149.spendr.domain.model.EntryListData
import com.judahben149.spendr.presentation.entry_list.epoxy.EntryListController
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.DateUtils.getMonthFromDateInMillis
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class EntryListFragment : Fragment() {

    private var _binding: FragmentEntryListBinding? = null
    val binding get() = _binding!!

    private val viewModel: EntryListViewModel by viewModels()
    private lateinit var entryListController: EntryListController
    private lateinit var entryListRv: EpoxyRecyclerView

    val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setEntryType()

        binding.toolBar.setOnClickListener {
            navController.popBackStack()
        }
        setupEpoxyController()

        lifecycleScope.launch {
            viewModel.pagedCashEntries.collectLatest { pagedCashEntries ->
                entryListController.submitData(pagedCashEntries)
            }
        }

        entryListController.addLoadStateListener { combinedLoadStates ->
            if (combinedLoadStates.append.endOfPaginationReached) {
                if (entryListController.adapter.itemCount < 1) {
                    binding.animEmptyBudget.visibility = View.VISIBLE
                    binding.tvEmptyBudget.visibility = View.VISIBLE
                } else {
                    binding.animEmptyBudget.visibility = View.INVISIBLE
                    binding.tvEmptyBudget.visibility = View.INVISIBLE
                }
            }
        }
    }

    private fun showOrHideToolBar(title: String) {
        val showToolBar = arguments?.getBoolean(Constants.SHOULD_SHOW_TOOL_BAR)

        showToolBar?.let { shouldShowToolBar ->
            if (shouldShowToolBar) {
                binding.tvToolbarTitle.text = title
                binding.tvToolbarTitle.animateToolBarTitle()
                return
            }
        }

        binding.toolBar.visibility = View.GONE
    }


    private fun setEntryType() {
        val isIncomeEntryType = arguments?.getBoolean(Constants.IS_INCOME_ENTRY_TYPE)

        isIncomeEntryType?.let {
            if (isIncomeEntryType) {
                showOrHideToolBar("Income")
                binding.tvToolbarTitle.setTextColor(resources.getColor(R.color.pigment_green))
                Timber.tag(Constants.TIMBER_TAG).d("Fragment update- is Income? - $isIncomeEntryType")
                viewModel.updateEntryListType(EntryListType.IncomeEntry)
            } else if (!isIncomeEntryType) {
                showOrHideToolBar("Expenditure")
                binding.tvToolbarTitle.setTextColor(resources.getColor(R.color.syracuse_red_orange))
                Timber.tag(Constants.TIMBER_TAG).d("Fragment update- is Income? - $isIncomeEntryType")
                viewModel.updateEntryListType(EntryListType.ExpenditureEntry)
            }
        }
    }

    private fun setupEpoxyController() {
        entryListRv = binding.epoxyRvEntryList

        entryListController = EntryListController(
            requireContext(),
            onEntryItemClicked = { itemId ->
                handleItemClicks(itemId)
        })
        entryListRv.setController(entryListController)
    }

    private fun handleItemClicks(itemId: Int) {
        val bundle = Bundle().apply {
            putInt(Constants.ENTRY_ID, itemId)
        }

        navController.navigate(R.id.entryDetailFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}