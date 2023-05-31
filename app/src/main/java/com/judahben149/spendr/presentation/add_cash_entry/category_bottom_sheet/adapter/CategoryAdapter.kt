package com.judahben149.spendr.presentation.add_cash_entry.category_bottom_sheet.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.judahben149.spendr.databinding.ItemRvCategoryBinding
import com.judahben149.spendr.domain.model.Category
import com.judahben149.spendr.utils.extensions.highlight
import com.judahben149.spendr.utils.extensions.mapCategoryIcon
import com.judahben149.spendr.utils.extensions.unHighlight

class CategoryAdapter(private val setSelectedCategoryId: (selectedId: Int) -> Unit) :
    ListAdapter<Category, CategoryAdapter.CategoryViewHolder>(CategoryDiffer()) {


    inner class CategoryViewHolder(private val binding: ItemRvCategoryBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bindItem(item: Category) {
            binding.textItemCategory.text = item.categoryName
            binding.imageItemCategory.mapCategoryIcon(item.categoryIconId)

            binding.itemRvCategory.setOnClickListener {
                setSelectedCategoryId(item.categoryId)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding =
            ItemRvCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }


    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bindItem(currentItem)
    }


    class CategoryDiffer() : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem.categoryId == newItem.categoryId
        }

        override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean {
            return oldItem == newItem
        }
    }
}