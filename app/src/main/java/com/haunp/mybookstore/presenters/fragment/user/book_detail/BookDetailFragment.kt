package com.haunp.mybookstore.presenters.fragment.user.book_detail

import android.net.Uri
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.BookDetailFragmentBinding
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.user.cart.CartFragment
import com.haunp.mybookstore.presenters.fragment.user.cart.CartViewModel
import com.haunp.mybookstore.presenters.fragment.user.home.HomeAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.text.NumberFormat
import java.util.Locale


class BookDetailFragment : BaseFragment<BookDetailFragmentBinding>() {
    private var book: BookEntity? = null
    override var isTerminalBackKeyActive: Boolean = true
    private val viewModel: CartViewModel by inject()
    private val rateViewModel: RateViewModel by inject()

    override fun getDataBinding(): BookDetailFragmentBinding {
        return BookDetailFragmentBinding.inflate(layoutInflater)
    }

    var adapter = RateAdapter()
    override fun initView() {
        binding {
            book = arguments?.getParcelable("book")
            book?.let {
                nameBook.text = it.title
                val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                    .format(it.price)
                priceBook.text = "$formattedPrice đ"
                authorBook.text = it.author
                descriptionBook.text = it.description
                Glide.with(binding.root.context)
                    .load(it.imageUri)
                    .into(binding.imgBook)
                Log.d("TAG", "initView: ${it.imageUri}")
            }
            rvRate.adapter = adapter
            rvRate.layoutManager = LinearLayoutManager(context)
            lifecycleScope.launch {
                rateViewModel.getListRate(book!!.bookId)
            }
            rateViewModel.rate.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }

    override fun initAction() {
        binding {
            btnAddToCart.setOnClickListener {
                lifecycleScope.launch {
                    viewModel.addBookToCart(BookStoreManager.idUser!!, book!!.bookId)
                }
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CartFragment())
                    .addToBackStack(null)
                    .commit()
            }
            btnSubmitComment.setOnClickListener {
                val comment = edtComment.text.toString()
                if (comment.isEmpty()) {
                    return@setOnClickListener
                }
                val rate = RateEntity(
                    userName = BookStoreManager.userName!!,
                    bookId = book!!.bookId,
                    rating = 5,
                    comment = comment
                )
                edtComment.text.clear()
                lifecycleScope.launch {
                    rateViewModel.addRate(rate)
                }
            }

        }
    }

}