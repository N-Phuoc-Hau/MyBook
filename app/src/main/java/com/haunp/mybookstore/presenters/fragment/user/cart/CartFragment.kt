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

    override fun initView() {
        binding {
            val adapter = CartAdapter()
            rVCart.adapter = adapter
            rVCart.layoutManager = LinearLayoutManager(context)
            viewModel.getBookInCart(BookStoreManager.idUser!!)
            viewModel.bookInCart.observe(viewLifecycleOwner) { books ->
                Log.d("hau.np", "initView: $books")
                adapter.submitList(books)
            }
        }
    }
}
