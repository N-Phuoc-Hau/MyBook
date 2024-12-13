package com.haunp.mybookstore.presenters.fragment.user.cart

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.CartFragmentBinding
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import kotlinx.coroutines.launch
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
//            viewModel.cart.observe(viewLifecycleOwner) { cart ->
//                val tmp = viewModel.getBookInCart(BookStoreManager.idUser!!)
//                Log.d("hau.np", "getBookInCart: $tmp")
            lifecycleScope.launch {
                viewModel.getBookInCart(BookStoreManager.idUser!!)
            }
            viewModel.bookInCart.observe(viewLifecycleOwner) { books ->
                Log.d("hau.np", "initView: $books")
                adapter.submitList(books)
//                    val bookIds = cart?.bookId?.split(",")?.mapNotNull { it.toIntOrNull() }
//                    val booksInCart = bookIds?.let { books.filter { book -> book.bookId in it } }

//                val bookIds = cart?.bookId?.split(",")?.mapNotNull { it.toIntOrNull() }
//                if (books != null) {
//                    adapter.submitList(books)
//                }
//                }
            }
        }
    }
}
