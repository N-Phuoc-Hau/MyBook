package com.haunp.mybookstore.presenters.fragment.user.setting

import android.util.Log
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.SettingFragmentBinding
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.login.LoginFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
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
        if (BookStoreManager.idUser != null) {
            binding.btnLogin.visibility = View.GONE
            binding.btnLogout.visibility = View.VISIBLE
            binding.rVOrder.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.textView3.text = "Tài khoản"
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
        }
    }
}