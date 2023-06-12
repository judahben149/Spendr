package com.judahben149.spendr.presentation.extras.reminders

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.DialogFragment
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.DialogNotificationPermissionBinding
import com.judahben149.spendr.utils.NotificationHelper

class NotificationPermissionDialog: DialogFragment() {

    private var _binding: DialogNotificationPermissionBinding? = null
    private val binding get() = _binding!!

    override fun getTheme() = R.style.RoundedCornersDialog

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = DialogNotificationPermissionBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOpenNotificationSettings.setOnClickListener {
            NotificationHelper(requireContext()).requestNotificationPermission()
            dismiss()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}