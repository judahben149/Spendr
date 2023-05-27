package com.judahben149.spendr.presentation.entry_list

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.judahben149.spendr.utils.Constants

class EntryListPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle): FragmentStateAdapter(fragmentManager, lifecycle) {


    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                EntryListFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(Constants.IS_INCOME_ENTRY_TYPE, true)
                        putBoolean(Constants.SHOULD_SHOW_TOOL_BAR, false)
                    }
                }
            }

            1 -> {
                EntryListFragment().apply {
                    arguments = Bundle().apply {
                        putBoolean(Constants.IS_INCOME_ENTRY_TYPE, false)
                        putBoolean(Constants.SHOULD_SHOW_TOOL_BAR, false)
                    }
                }
            }

            else -> {
                throw IllegalArgumentException("Invalid position - $position")
            }
        }
    }
}