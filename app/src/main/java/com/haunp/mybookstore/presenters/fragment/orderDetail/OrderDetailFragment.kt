package com.haunp.mybookstore.presenters.fragment.orderDetail

import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.OrderDetailFragmentBinding
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.OrderAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.android.ext.android.inject

class OrderDetailFragment : BaseFragment<OrderDetailFragmentBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    private var order: OrderEntity? = null
    private val viewModel: OrderDetailViewModel by inject()
    override fun getDataBinding(): OrderDetailFragmentBinding {
        return OrderDetailFragmentBinding.inflate(layoutInflater)
    }

    private var adapter = OrderAdapter()
    private var orderDetailAdapter = OrderDetailAdapter()
    override fun initView() {
        binding {
            rvOrderDetail.adapter = orderDetailAdapter
            rvOrderDetail.layoutManager = LinearLayoutManager(context)
        }
        order = arguments?.getParcelable("order")
        lifecycleScope.launch {
            val orderList = viewModel.getOrderDetail(order!!.orderId)
            Log.d("hau.np", "orderList: $orderList")
            val bookList = mutableListOf<BookEntity>()
            withContext(Dispatchers.IO) {
                orderList.forEach { orderDetail ->
                    val updatedList = viewModel.addBook(orderDetail.bookId)
                    Log.d("hau.np", "updatedList: $updatedList")
                    bookList.addAll(updatedList) // Thêm sách từ `updatedList` vào `bookList`
                }
            }
//
//            // Kiểm tra danh sách sách và cập nhật Adapter
            withContext(Dispatchers.Main) {
                orderDetailAdapter.submitList(bookList)
            }
        }
    }
}