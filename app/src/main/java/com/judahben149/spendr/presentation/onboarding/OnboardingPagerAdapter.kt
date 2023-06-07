package com.judahben149.spendr.presentation.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.judahben149.spendr.utils.Constants

class OnboardingPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> {
                OnboardingContentFragment().apply {
                    arguments = Bundle().apply {
                        putInt(Constants.ONBOARDING_FRAGMENT_PAGE, 1)
                    }
                }
            }

            1 -> {
                OnboardingContentFragment().apply {
                    arguments = Bundle().apply {
                        putInt(Constants.ONBOARDING_FRAGMENT_PAGE, 2)
                    }
                }
            }

            2 -> {
                OnboardingContentFragment().apply {
                    arguments = Bundle().apply {
                        putInt(Constants.ONBOARDING_FRAGMENT_PAGE, 3)
                    }
                }
            }

            else -> {
                throw IllegalArgumentException("Invalid position - $position")
            }
        }
    }
}