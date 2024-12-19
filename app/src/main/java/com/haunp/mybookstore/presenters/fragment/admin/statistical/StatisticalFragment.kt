package com.haunp.mybookstore.presenters.fragment.admin.statistical

import android.os.Bundle
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.StatisticalFragmentBinding
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.orderDetail.OrderDetailFragment
import com.haunp.mybookstore.presenters.fragment.user.setting.OrderAdapter
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class StatisticalFragment : BaseFragment<StatisticalFragmentBinding>() {
    private val viewModel: StatisticalViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): StatisticalFragmentBinding {
        return StatisticalFragmentBinding.inflate(layoutInflater)
    }

    var adapter = StatisticalAdapter()
    override fun initView() {
        binding.rvOrder.adapter = adapter
        binding.rvOrder.layoutManager = LinearLayoutManager(context)
        lifecycleScope.launch {
            viewModel.getAllOrder()
            viewModel.statistical.observe(viewLifecycleOwner) { orders ->
                adapter.submitList(orders)
            }
        }
    }

    override fun initAction() {
        binding {
            edtSearch.addTextChangedListener { searchText ->
                val query = searchText.toString().lowercase()
                val orderList = viewModel.statistical.value
                val filteredOrders = orderList?.filter { order ->
                    order.first.orderDate.toString().contains(query) ||
                    order.second.toString().contains(query)
                }
                if (filteredOrders != null) {
                    adapter.submitList(filteredOrders)
                }
            }
            adapter.onItemClick = {order->
                val orderDetailFragment = OrderDetailFragment()
                val bundle = Bundle().apply {
                    putParcelable("order", order)
                }
                orderDetailFragment.arguments = bundle
                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, orderDetailFragment)
                    .addToBackStack(null)
                    .commit()
            }
        }
    }
}