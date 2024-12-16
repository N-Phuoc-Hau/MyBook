package com.haunp.mybookstore.presenters.fragment.orderDetail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemOrderBinding
import com.haunp.mybookstore.domain.model.BookEntity
import java.text.NumberFormat
import java.util.Locale

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {

    private val books = mutableListOf<BookEntity>()// Danh sách dữ liệu hiển thị

    fun submitList(newBooks: List<BookEntity>){
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    inner class OrderDetailViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                .format(book.price)
            binding.tvPrice.text = "$formattedPrice đ"
            Glide.with(binding.root.context)
                .load(book.imageUri)
                .into(binding.imgBook)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(books[position])
    }
    override fun getItemCount(): Int = books.size
}