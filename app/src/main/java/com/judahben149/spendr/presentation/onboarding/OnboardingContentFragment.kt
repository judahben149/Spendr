package com.judahben149.spendr.presentation.onboarding

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.preference.PreferenceManager
import androidx.viewpager2.widget.ViewPager2
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentOnboardingContentBinding
import com.judahben149.spendr.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OnboardingContentFragment : Fragment() {

    private var _binding: FragmentOnboardingContentBinding? = null
    val binding get() = _binding!!

    val navController by lazy {
        findNavController()
    }

    @Inject
    lateinit var appPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingContentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setPageDetails()
    }

    private fun setPageDetails() {
        val onboardingPage = arguments?.getInt(Constants.ONBOARDING_FRAGMENT_PAGE) ?: 1

        when (onboardingPage) {
            1 -> {
                binding.tvLeftText.visibility = View.INVISIBLE
                binding.layoutTvUserName.visibility = View.INVISIBLE
                binding.tvOnboardingTitle.text = getString(R.string.tv_onboarding_title_1)
                binding.tvOnboardingText.text = getString(R.string.tv_onboarding_text_1)
                binding.animOnboarding.apply {
                    setAnimation("anim_piggy_bank.json")
                    speed = 0.13F
                    setMinAndMaxProgress(0.0F, 0.7F)
                }

                binding.tvRightText.setOnClickListener {
                    val viewPager =
                        requireParentFragment().view?.findViewById<ViewPager2>(R.id.onboarding_viewpager)
                    viewPager?.currentItem = 1
                }
            }

            2 -> {
                binding.layoutTvUserName.visibility = View.INVISIBLE
                binding.tvOnboardingTitle.text = getString(R.string.tv_onboarding_title_2)
                binding.tvOnboardingText.text = getString(R.string.tv_onboarding_text_2)
                binding.animOnboarding.apply {
                    setAnimation("anim_visualize.json")
                    speed = 0.6F
                }

                binding.tvLeftText.setOnClickListener {
                    val viewPager =
                        requireParentFragment().view?.findViewById<ViewPager2>(R.id.onboarding_viewpager)
                    viewPager?.currentItem = 0
                }

                binding.tvRightText.setOnClickListener {
                    val viewPager =
                        requireParentFragment().view?.findViewById<ViewPager2>(R.id.onboarding_viewpager)
                    viewPager?.currentItem = 2
                }
            }

            3 -> {
                binding.tvOnboardingTitle.text = getString(R.string.tv_onboarding_title_3)
                binding.tvOnboardingText.text = getString(R.string.tv_onboarding_text_3)
                binding.tvRightText.text = "Start"
                binding.animOnboarding.apply {
                    setAnimation("anim_calendar.json")
                    speed = 0.45F
                }

                binding.tvLeftText.setOnClickListener {
                    val viewPager =
                        requireParentFragment().view?.findViewById<ViewPager2>(R.id.onboarding_viewpager)
                    viewPager?.currentItem = 1
                }

                binding.tvRightText.setOnClickListener {
                    val userName = binding.tvUserName.text.toString()

                    val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
                    val editor = appPrefs.edit()
                    val prefsEditor = prefs.edit()
                    prefsEditor?.putString(Constants.USER_NAME, userName)?.apply()
                    editor?.putBoolean(Constants.IS_FIRST_LAUNCH, false)?.apply()
                    navController.navigate(R.id.homeFragment)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}