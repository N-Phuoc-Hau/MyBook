package com.haunp.mybookstore.presenters.fragment.user.category_user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemCategoryBinding
import com.haunp.mybookstore.domain.model.CategoryEntity

class CategoryUserAdapter() : RecyclerView.Adapter<CategoryUserAdapter.CategoryUserViewHolder>() {
    var onItemClick : (CategoryEntity) -> Unit = {}
    private val categories = mutableListOf<CategoryEntity>()

    fun submitList(list: List<CategoryEntity>) {
        categories.clear()
        categories.addAll(list)
        notifyDataSetChanged()
    }

    inner class CategoryUserViewHolder(private val binding: ItemCategoryBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(category: CategoryEntity) {
            binding.apply {
                // Bind dữ liệu category
                tvCategoryName.text = category.name
                Glide.with(binding.root.context)
                    .load(category.imageUri.toUri())
                    .into(ivCategoryImage)
                root.setOnClickListener {
                    onItemClick(category)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryUserViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryUserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryUserViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount() = categories.size
}