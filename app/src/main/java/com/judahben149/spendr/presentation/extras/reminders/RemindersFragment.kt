package com.judahben149.spendr.presentation.extras.reminders

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.spendr.R
import com.judahben149.spendr.databinding.FragmentRemindersBinding
import com.judahben149.spendr.presentation.extras.reminders.adapter.RemindersAdapter
import com.judahben149.spendr.utils.Constants
import com.judahben149.spendr.utils.Constants.TIMBER_TAG
import com.judahben149.spendr.utils.NotificationHelper
import com.judahben149.spendr.utils.extensions.animateToolBarTitle
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RemindersFragment : Fragment() {

    private var _binding: FragmentRemindersBinding? = null
    val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    private lateinit var remindersAdapter: RemindersAdapter
    private val viewModel: RemindersViewModel by activityViewModels()

    private val navController by lazy {
        findNavController()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRemindersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvToolbarTitle.animateToolBarTitle()
        binding.btnBack.setOnClickListener {
            navController.navigateUp()
        }
        viewModel.reset()
        viewModel.getReminders()
        setupRecyclerView()

        binding.ivNewReminder.setOnClickListener {
            if (NotificationHelper(requireContext()).isNotificationPermissionGranted())
                RemindersContainerBottomSheet().show(childFragmentManager, Constants.REMINDER_BOTTOM_SHEET)
            else
                NotificationPermissionDialog().show(childFragmentManager, Constants.NOTIFICATION_PERMISSION_DIALOG)
        }

        viewModel.reminderListState.observe(viewLifecycleOwner) { reminderListState ->
            shouldShowEmptyAnimation(reminderListState.reminders.size)
            remindersAdapter.submitList(reminderListState.reminders)
        }
    }

    private fun shouldShowEmptyAnimation(listSize: Int) {
        if (listSize < 1) {
            binding.animEmptyReminder.visibility = View.VISIBLE
            binding.tvEmptyReminder.visibility = View.VISIBLE
            Timber.tag(TIMBER_TAG).d("Showing animation")
        } else {
            binding.animEmptyReminder.visibility = View.INVISIBLE
            binding.tvEmptyReminder.visibility = View.INVISIBLE
            Timber.tag(TIMBER_TAG).d("Hiding animation")
        }
    }

    fun setupRecyclerView() {
        recyclerView = binding.rvReminderList
        remindersAdapter = RemindersAdapter(requireContext()) { id ->
            //pull up bottom sheet to update reminder
        }

        recyclerView.adapter = remindersAdapter
        recyclerView.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}