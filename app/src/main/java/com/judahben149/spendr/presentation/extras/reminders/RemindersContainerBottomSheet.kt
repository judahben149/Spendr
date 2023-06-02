package com.judahben149.spendr.presentation.extras.reminders

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentBottomSheetContainerBinding
import com.judahben149.spendr.databinding.FragmentCategoryBottomSheetBinding
import com.judahben149.spendr.databinding.FragmentRemindersContainerBottomSheetBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RemindersContainerBottomSheet: BottomSheetDialogFragment() {

    private var _binding: FragmentRemindersContainerBottomSheetBinding? = null
    val binding get() = _binding!!

    private lateinit var navHostFragment: NavHostFragment
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemindersContainerBottomSheetBinding.inflate(inflater, container, false)
        setupNavigation()
        return binding.root
    }

    private fun setupNavigation() {
        navHostFragment = childFragmentManager.findFragmentById(R.id.reminders_bottom_sheet_nav_host) as NavHostFragment
        navController = navHostFragment.navController
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}