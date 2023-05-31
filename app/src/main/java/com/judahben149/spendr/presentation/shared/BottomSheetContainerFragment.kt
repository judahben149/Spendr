package com.judahben149.spendr.presentation.shared

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentBottomSheetContainerBinding
import com.judahben149.spendr.presentation.add_cash_entry.AddCashEntryViewModel

class BottomSheetContainerFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetContainerBinding? = null
    val binding get() = _binding!!

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController

    private val viewModel: AddCashEntryViewModel by viewModels({ requireParentFragment() })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetContainerBinding.inflate(inflater, container, false)
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        navHostFragment = childFragmentManager.findFragmentById(R.id.bottom_sheet_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}