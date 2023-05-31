package com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.spendr.databinding.ItemRvCategoryIconBinding
import com.judahben149.spendr.utils.extensions.mapCategoryIcon

class CategoryIconAdapter(private val selectedIconId:(id: Int) -> Unit): ListAdapter<Int, CategoryIconAdapter.CategoryIconViewHolder>(
    CategoryIconDiffer()
) {


    inner class CategoryIconViewHolder(private val binding: ItemRvCategoryIconBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Int) {
            binding.imageItemCategory.mapCategoryIcon(item)

            binding.itemRvCategory.setOnClickListener {
                selectedIconId(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryIconViewHolder {
        val binding =
            ItemRvCategoryIconBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryIconViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryIconViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bindItem(currentItem)
    }


    class CategoryIconDiffer() : DiffUtil.ItemCallback<Int>() {
        override fun areItemsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Int, newItem: Int): Boolean {
            return oldItem == newItem
        }
    }
}