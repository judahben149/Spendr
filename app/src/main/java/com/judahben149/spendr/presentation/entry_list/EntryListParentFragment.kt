package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.judahben149.spendr.databinding.FragmentEntryListParentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EntryListParentFragment : Fragment() {

    private var _binding: FragmentEntryListParentBinding? = null
    val binding get() = _binding!!

    private lateinit var pagerAdapter: EntryListPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEntryListParentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewPager = binding.viewPagerEntryListParent
        val tabLayout = binding.tabLayoutEntryList

        pagerAdapter = EntryListPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter

        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = getTabTitle(position)
        }.attach()
    }

    private fun getTabTitle(position: Int): String {
        return when(position) {
            0 -> "Income"
            1 -> "Expenditure"
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}