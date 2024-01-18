package com.judahben149.spendr.presentation.onboarding

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentOnboardingBinding
import com.judahben149.spendr.presentation.entry_list.EntryListPagerAdapter
import com.judahben149.spendr.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingFragment : Fragment() {

    private var _binding: FragmentOnboardingBinding? = null
    val binding get() = _binding!!

    private lateinit var pagerAdapter: OnboardingPagerAdapter

    val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var appPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val isFirstLaunch = appPrefs.getBoolean(Constants.IS_FIRST_LAUNCH, true)
        if (!isFirstLaunch)
            navController.navigate(R.id.action_onboardingFragment_to_homeFragment)

        val viewPager = binding.onboardingViewpager
        pagerAdapter = OnboardingPagerAdapter(childFragmentManager, lifecycle)
        viewPager.adapter = pagerAdapter
        binding.dotIndicatorOnboarding.attachTo(viewPager)

        viewPager.currentItem
    }



    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}