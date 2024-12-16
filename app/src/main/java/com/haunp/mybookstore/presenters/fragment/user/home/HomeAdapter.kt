package com.haunp.mybookstore.presenters.fragment.user.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemBookBinding
import com.haunp.mybookstore.domain.model.BookEntity
import java.text.NumberFormat
import java.util.Locale

class   HomeAdapter: RecyclerView.Adapter<HomeAdapter.HomeViewHolder>() {
    var onItemClick : (BookEntity) -> Unit = {}
    private val books = mutableListOf<BookEntity>()
    fun submitList(newBooks: List<BookEntity>){
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
    inner class HomeViewHolder(private val binding: ItemBookBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(book: BookEntity) {
            binding.tvTittle.text = book.title
            val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                .format(book.price)
            binding.tvPrice.text = "$formattedPrice đ"
            Glide.with(binding.root.context)
                .load(book.imageUri)
                .into(binding.imgBook)

            binding.root.setOnClickListener {
                onItemClick(book)
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemBookBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}