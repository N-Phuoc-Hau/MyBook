package com.haunp.mybookstore.presenters.fragment.user.cart

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemBookBinding
import com.haunp.mybookstore.databinding.ItemCartBinding
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.entity.CartEntity

class CartAdapter : ListAdapter<BookEntity, CartAdapter.CartViewHolder>(BookDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val book = getItem(position) // Sử dụng getItem để lấy dữ liệu từ danh sách
        holder.bind(book)
    }

    override fun getItemCount(): Int = currentList.size // Sử dụng currentList của ListAdapter

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            Log.d("hau.np", "bind: $book")
            binding.tvTitle.text = book.title
            binding.tvPrice.text = book.price.toString()
            Glide.with(binding.root.context)
                .load(book.imageUri)
                .into(binding.imgIcon)
        }
    }
}