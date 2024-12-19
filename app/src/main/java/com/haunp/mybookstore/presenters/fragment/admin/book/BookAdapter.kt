package com.haunp.mybookstore.presenters.fragment.admin.book

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemBookAdminBinding
import com.haunp.mybookstore.domain.model.BookEntity
import java.text.NumberFormat
import java.util.Locale


class BookAdapter(private val viewModel: BookViewModel, private val updateBook: (categoryId: Int) -> Unit) : RecyclerView.Adapter<BookAdapter.BookViewHolder>() {
    private val books = mutableListOf<BookEntity>() // Danh sách dữ liệu hiển thị

    fun submitList(newBooks: List<BookEntity>){
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }
    inner class BookViewHolder(private val binding: ItemBookAdminBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(book: BookEntity) {
                    binding.tvTitle.text = book.title
                    binding.tvAuthor.text = book.author
                    binding.tvQuantity.text = book.quantity.toString()
                    val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                        .format(book.price)
                    binding.tvPrice.text = "$formattedPrice đ"
                    Glide.with(binding.root.context)
                        .load(book.imageUri)
                        .into(binding.imgSelectedBook)
                    binding.imgDelete.setOnClickListener(){
                        viewModel.deleteBook(book.bookId)
                    }
                    binding.imgUpdate.setOnClickListener(){
                        updateBook(book.bookId)
                    }
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder {
        val binding = ItemBookAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        holder.bind(books[position])
    }

    override fun getItemCount(): Int = books.size
}