package com.haunp.mybookstore.presenters.fragment.user.cart

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.CartFragmentBinding
import com.haunp.mybookstore.domain.entity.OrderDetailEntity
import com.haunp.mybookstore.domain.entity.OrderEntity
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.SettingViewModel
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import java.time.LocalDate

class CartFragment : BaseFragment<CartFragmentBinding>() {

    private val viewModel: CartViewModel by inject()
    private val orderViewModel : SettingViewModel by inject()
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
                val listBook = books
                adapter.submitList(listBook)
                tvPrice.text = books.sumOf { it.price }.toString()
            }
        }
    }

    override fun initAction() {
        binding {
            btnThanhToan.setOnClickListener {
                checkout()
            }
        }
    }
    private fun checkout(){
        lifecycleScope.launch {
            val userId = BookStoreManager.idUser
            val books = viewModel.bookInCart.value
            if(books!!.isEmpty()){
                Toast.makeText(context, "Giỏ hàng trống", Toast.LENGTH_SHORT).show()
                return@launch
            }
            val totalAmount = books.sumOf { it.price }
            val tmp = OrderEntity(userId = userId!!, orderDate = LocalDate.now().toString(), quantity = books.size, totalAmount = totalAmount)
            val order = orderViewModel.insertOrder(tmp,userId)

            val orderDetails = books.map { book ->
                OrderDetailEntity(
                    orderId = order.toInt(), // ID của đơn hàng
                    bookId = book.bookId,      // ID của sách
                    quantity = 1,             // Số lượng mặc định là 1
                    price = book.price        // Giá sách
                )
            }
            Toast.makeText(context, "Đặt hàng thành công", Toast.LENGTH_SHORT).show()
            viewModel.clearCart(userId)
            (activity as MainActivity).showFragment(SettingFragment())
        }

    }
}
