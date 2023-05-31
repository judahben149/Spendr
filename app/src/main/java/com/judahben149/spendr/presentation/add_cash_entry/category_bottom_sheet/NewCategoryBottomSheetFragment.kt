package com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentCategoryBottomSheetBinding
import com.judahben149.spendr.databinding.FragmentNewCategoryBottomSheetBinding
import com.judahben149.spendr.presentation.add_cash_entry.AddCashEntryViewModel
import com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.adapter.CategoryAdapter
import com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.adapter.CategoryIconAdapter
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

class NewCategoryBottomSheetFragment : Fragment() {

    private var _binding: FragmentNewCategoryBottomSheetBinding? = null
    val binding get() = _binding!!

    private lateinit var adapter: CategoryIconAdapter
    private lateinit var recyclerView: RecyclerView

    private val viewModel: CategoryViewModel by activityViewModels()

    private var selectedIconId: Int? = null
    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNewCategoryBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()


        adapter.submitList(viewModel.getCategoryIconList())

        binding.ivBtnBack.setOnClickListener {
            navController.popBackStack()
        }

        binding.tvSave.setOnClickListener {
            if (binding.etNameNewCategory.text.isNullOrEmpty()) {

            } else {

                val categoryName = binding.etNameNewCategory.text.toString()
                //save in any negative id for selected id. The else function in
                // the mapping fxn will assign the uncategorized icon to it
                viewModel.saveNewCategory(categoryName, selectedIconId ?: -1)
                navController.popBackStack()
            }
        }
    }

    private fun setupRecyclerView() {
        recyclerView = binding.rvChooseIcon
        adapter = CategoryIconAdapter() { selectedIconId ->
            this.selectedIconId = selectedIconId
            binding.ivSelectedIcon.mapCategoryIcon(selectedIconId)
            //set this in viewModel also
        }
        recyclerView.adapter = adapter
    }
}