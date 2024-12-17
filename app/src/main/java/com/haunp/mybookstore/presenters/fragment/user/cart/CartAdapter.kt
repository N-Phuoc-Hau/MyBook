package com.haunp.mybookstore.presenters.fragment.user.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.haunp.mybookstore.databinding.ItemCartBinding
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.OnQuantityChangedListener
import com.haunp.mybookstore.presenters.BookStoreManager
import java.text.NumberFormat
import java.util.Locale

class CartAdapter(private val cartViewModel: CartViewModel) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {
    private val books = mutableListOf<BookEntity>() // Danh sách dữ liệu hiển thị
    var onQuantityChangedListener: OnQuantityChangedListener? = null
    fun submitList(newBooks: List<BookEntity>) {
        books.clear()
        books.addAll(newBooks)
        notifyDataSetChanged()
    }

    inner class CartViewHolder(private val binding: ItemCartBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(book: BookEntity) {
                binding.tvTitle.text = book.title
                val quantity = binding.tvQuantity.text.toString().toInt()
                val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                    .format(book.price * quantity)
                binding.tvPrice.text = "$formattedPrice đ"
                Glide.with(binding.root.context)
                    .load(book.imageUri)
                    .into(binding.imgIcon)

                binding.btnMinus.setOnClickListener {
                    val currentQuantity = binding.tvQuantity.text.toString().toInt()
                    if (currentQuantity > 1) {
                        val newQuantity = currentQuantity - 1
                        binding.tvQuantity.text = newQuantity.toString()
                        val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                            .format(book.price * newQuantity)
                        binding.tvPrice.text = "$formattedPrice đ"
                        onQuantityChangedListener?.onQuantityChanged(book.bookId, newQuantity, book.price * newQuantity)
                    }
                }
                binding.btnPlus.setOnClickListener {
                    val currentQuantity = binding.tvQuantity.text.toString().toInt()
                    val newQuantity = currentQuantity + 1
                    binding.tvQuantity.text = newQuantity.toString()
                    val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                        .format(book.price * newQuantity)
                    binding.tvPrice.text = "$formattedPrice đ"
                    onQuantityChangedListener?.onQuantityChanged(book.bookId, newQuantity, book.price * newQuantity)
                }
                binding.btnRemove.setOnClickListener {
                    val bookToRemove = books[adapterPosition]
                    cartViewModel.deleteBookInCart(bookToRemove.bookId, BookStoreManager.idUser!!)
                }
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