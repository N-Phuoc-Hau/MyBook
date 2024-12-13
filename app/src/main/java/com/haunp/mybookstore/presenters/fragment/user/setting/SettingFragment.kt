package com.haunp.mybookstore.presenters.fragment.user.setting

import android.view.View
import androidx.fragment.app.activityViewModels
import com.haunp.mybookstore.databinding.SettingFragmentBinding
import com.haunp.mybookstore.presenters.BookStoreManager
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.login.LoginFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity

class SettingFragment : BaseFragment<SettingFragmentBinding>() {
    //shareViewModel
    private val coreViewModel: CoreViewModel by activityViewModels()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): SettingFragmentBinding {
        return SettingFragmentBinding.inflate(layoutInflater)
    }

    override fun initView() {
//        coreViewModel.user.observe(viewLifecycleOwner) { user ->
        if (BookStoreManager.idUser != null) {
            binding.btnLogin.visibility = View.GONE
//                binding.textView3.text = user?.username
            binding.btnLogout.visibility = View.VISIBLE
            binding.rvOrder.visibility = View.VISIBLE
        } else {
            binding.btnLogin.visibility = View.VISIBLE
            binding.textView3.text = "Tài khoản"
            binding.btnLogout.visibility = View.GONE
            binding.rvOrder.visibility = View.GONE
        }
//        }
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