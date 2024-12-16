package com.haunp.mybookstore.presenters.fragment.user.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemCartBinding
import com.haunp.mybookstore.domain.model.BookEntity

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private var books = mutableListOf<BookEntity>() // Danh sách dữ liệu hiển thị

    fun submitList(newBooks: List<BookEntity>) {
        books = newBooks.toMutableList()
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(book: BookEntity) {
                binding.tvTitle.text = book.title
                binding.tvPrice.text = book.price.toString()
                Glide.with(binding.root.context)
                    .load(book.imageUri)
                    .into(binding.imgIcon)
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(
            LayoutInflater.from(parent.context), parent,false)
        return CartViewHolder(binding)
    }
    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(books[position])
    }
    override fun getItemCount(): Int = books.size
}