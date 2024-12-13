package com.haunp.mybookstore.presenters.fragment.user.cart

import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.CartFragmentBinding
import com.haunp.mybookstore.presenters.base.BaseFragment
import org.koin.android.ext.android.inject

class CartFragment : BaseFragment<CartFragmentBinding>() {

    private val viewModel: CartViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true
    override fun getDataBinding(): CartFragmentBinding {
        return CartFragmentBinding.inflate(layoutInflater)
    }

    var adapter = CartAdapter()
    override fun initView() {

        binding {
            rVCart.layoutManager = LinearLayoutManager(context)
            rVCart.adapter = adapter
            viewModel.cart.observe(viewLifecycleOwner) { cart ->
                viewModel.bookInCart.observe(viewLifecycleOwner) { books ->
                    val bookIds = cart?.bookId?.split(",")?.mapNotNull { it.toIntOrNull() }
                    val booksInCart = bookIds?.let { books.filter { book -> book.bookId in it } }
                    if (booksInCart != null) {
                        adapter.submitList(booksInCart)
                    }
//                val bookIds = cart?.bookId?.split(",")?.mapNotNull { it.toIntOrNull() }
//                val books = bookIds?.let { viewModel.getBookInCart(BookStoreManager.idUser!!) }
//                if (books != null) {
//                    adapter.submitList(books)
//                }
                }
            }
        }
    }
}
