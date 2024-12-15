package com.haunp.mybookstore.presenters.fragment.user

import androidx.lifecycle.lifecycleScope
import com.haunp.mybookstore.databinding.OrderDetailFragmentBinding
import com.haunp.mybookstore.presenters.base.BaseFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class OrderDetailFragment: BaseFragment<OrderDetailFragmentBinding>() {
    override var isTerminalBackKeyActive: Boolean = true
    private val viewModel: OrderDetailViewModel by inject()
    override fun getDataBinding(): OrderDetailFragmentBinding {
        return OrderDetailFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
        lifecycleScope.launch{
//            viewModel.getOrderDetail(1)
        }
    }
}