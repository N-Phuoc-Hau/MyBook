package com.haunp.mybookstore.presenters.fragment.user.setting

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.R
import com.haunp.mybookstore.databinding.SettingFragmentBinding
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.login.LoginFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.orderDetail.OrderDetailFragment
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class SettingFragment : BaseFragment<SettingFragmentBinding>() {
    private val coreViewModel: CoreViewModel by activityViewModels()
    private val viewModel: SettingViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): SettingFragmentBinding {
        return SettingFragmentBinding.inflate(layoutInflater)
    }
    var adapter = OrderAdapter()
    override fun initView() {
        val nameUser = BookStoreManager.userName
        if (BookStoreManager.idUser != null) {
            binding.btnLogin.visibility = View.GONE
            binding.textView2.text = "Chào mừng $nameUser đến với BookStore"
            binding.btnLogout.visibility = View.VISIBLE
            binding.rVOrder.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.textView3.text = "Tài khoản"
            binding.textView2.text = "Chào mừng bạn đến với BookStore"
            binding.btnLogout.visibility = View.GONE
            binding.rVOrder.visibility = View.GONE
        }
        if (BookStoreManager.idUser != null) {
            lifecycleScope.launch {
                binding.rVOrder.adapter = adapter
                binding.rVOrder.layoutManager = LinearLayoutManager(context)
                viewModel.getOrder(BookStoreManager.idUser!!)
                viewModel.orders.observe(viewLifecycleOwner) { orders->
                    Log.d("hau.np","initView: $orders")
                    adapter.submitList(orders)
                }
            }
        }
    }

    override fun initAction() {
        binding {
            btnLogin.setOnClickListener {
                (activity as MainActivity).showFragment(LoginFragment())
            }
            btnLogout.setOnClickListener {
                coreViewModel.logout()
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