package com.judahben149.spendr.presentation.extras.reminders.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.spendr.databinding.ItemRvRemindersBinding
import com.judahben149.spendr.domain.model.Reminder
import com.judahben149.spendr.utils.DateUtils
import com.judahben149.spendr.utils.extensions.abbreviateNumber

class RemindersAdapter(private val onReminderItemClicked:(id: Int) -> Unit): ListAdapter<Reminder, RemindersAdapter.RemindersViewHolder>(RemindersDiffer()) {

    inner class RemindersViewHolder(val binding: ItemRvRemindersBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Reminder) {
            binding.tvReminderAmount.text = item.amount.abbreviateNumber()
            binding.tvReminderDate.text = DateUtils.formatFriendlyDateTime(item.targetDate)
            binding.tvReminderText.text = item.reminderText

            if (item.isRecurrent) {
                binding.ivRecurrentReminder.visibility = View.VISIBLE
            } else {
                binding.ivRecurrentReminder.visibility = View.INVISIBLE
            }

            binding.cardReminder.setOnClickListener {
                onReminderItemClicked(item.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemindersViewHolder {
        val binding = ItemRvRemindersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RemindersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RemindersViewHolder, position: Int) {
        holder.bind(getItem(position))
    }


    class RemindersDiffer(): DiffUtil.ItemCallback<Reminder>() {
        override fun areItemsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Reminder, newItem: Reminder): Boolean {
            return oldItem == newItem
        }
    }
}