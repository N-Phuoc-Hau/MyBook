package com.haunp.mybookstore.presenters.fragment.user.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemCartBinding
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.entity.CartEntity

class CartAdapter : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val cartItems = mutableListOf<BookEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(newCart: List<BookEntity>) {
        cartItems.clear()
        cartItems.addAll(newCart)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(list: List<BookEntity>) {
            binding.apply {
                list.forEach { book ->
                    Glide.with(itemView.context)
                        .load(book.imageUri)
                        .into(imgIcon)
                    tvTitle.text = book.title
                    tvPrice.text = book.price.toString()
                    tvQuantity.text = book.quantity.toString()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cart = cartItems
        holder.bind(cart)
    }


    override fun getItemCount(): Int = cartItems.size
}
