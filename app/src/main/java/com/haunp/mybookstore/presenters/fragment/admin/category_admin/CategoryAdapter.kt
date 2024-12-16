package com.haunp.mybookstore.presenters.fragment.admin.category_admin

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemCategoryBinding
import com.haunp.mybookstore.domain.model.CategoryEntity

class CategoryAdapter : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    private val categories = mutableListOf<CategoryEntity>() // Danh sách dữ liệu hiển thị

    // Hàm để cập nhật dữ liệu danh mục
    fun submitList(newCategories: List<CategoryEntity>) {
        categories.clear()
        categories.addAll(newCategories)
        notifyDataSetChanged()
    }

    // ViewHolder cho từng item danh mục
    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(category: CategoryEntity) {
            binding.tvCategoryName.text = category.name
            Glide.with(binding.root.context)
                .load(category.imageUri) // Load ảnh từ URI
                .into(binding.ivCategoryImage) // ImageView hiển thị ảnh
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(categories[position])
    }

    override fun getItemCount(): Int = categories.size
}
