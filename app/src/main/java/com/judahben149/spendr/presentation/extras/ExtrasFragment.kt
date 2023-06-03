package com.judahben149.spendr.presentation.extras

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentExtrasBinding
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ExtrasFragment : Fragment() {

    private var _binding: FragmentExtrasBinding? = null
    val binding get() = _binding!!

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExtrasBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }

        binding.cardReminders.setOnClickListener {
            navController.navigate(R.id.action_extrasFragment_to_remindersFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}