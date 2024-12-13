package com.haunp.mybookstore.presenters.fragment.user

import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.BookDetailFragmentBinding
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.user.cart.CartFragment
import com.haunp.mybookstore.presenters.fragment.user.cart.CartViewModel
import com.haunp.mybookstore.presenters.fragment.user.home.HomeAdapter
import org.koin.android.ext.android.inject


class BookDetailFragment : BaseFragment<BookDetailFragmentBinding>() {
    private var book: BookEntity? = null
    override var isTerminalBackKeyActive: Boolean = true
    private val viewModel: CartViewModel by inject()

    override fun getDataBinding(): BookDetailFragmentBinding {
        return BookDetailFragmentBinding.inflate(layoutInflater)
    }

    var adapter = HomeAdapter()
    override fun initView() {
        binding {
            book = arguments?.getParcelable("book")
            book?.let {
                nameBook.text = it.title
                priceBook.text = it.price.toString()
                authorBook.text = it.author
                descriptionBook.text = it.description
                Glide.with(requireContext())
                    .load(it.imageUri) // Nếu imageUri là URL hoặc đường dẫn tệp
                    .into(imgBook)
            }
        }
    }

    override fun initAction() {
        binding {
            btnAddToCart.setOnClickListener {

                viewModel.addBookToCart(BookStoreManager.idUser!!, book!!.bookId)
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, CartFragment())
                    .addToBackStack(null)
                    .commit()
            }

        }
    }

}