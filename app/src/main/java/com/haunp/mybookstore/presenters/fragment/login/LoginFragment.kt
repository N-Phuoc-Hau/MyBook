package com.haunp.mybookstore.presenters.fragment.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import com.haunp.mybookstore.databinding.FragmentLoginBinding
import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.presenters.CoreViewModel
import com.haunp.mybookstore.presenters.base.BaseFragment
import com.haunp.mybookstore.presenters.fragment.admin.book.BookFragment
import com.haunp.mybookstore.presenters.fragment.main.MainActivity
import com.haunp.mybookstore.presenters.fragment.register.RegisterFragment
import org.koin.android.ext.android.inject

class LoginFragment : BaseFragment<FragmentLoginBinding>() {
    override var isTerminalBackKeyActive: Boolean = false

    private val viewModel: LoginViewModel by inject()
    private val coreViewModel : CoreViewModel by activityViewModels()

    override fun getDataBinding(): FragmentLoginBinding {
        return FragmentLoginBinding.inflate(layoutInflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun initView() {
        viewModel.loginResult.observe(viewLifecycleOwner) {
            if (it != null) {
                Toast.makeText(context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                (activity as MainActivity).showFragment(BookFragment())
                coreViewModel.setUser(it)
            }
            else {
                Toast.makeText(context, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show()
            }
        }

    }

    override fun initAction() {
        binding {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context,"Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                else if(username == "admin" && password == "admin"){
                    (activity as MainActivity).showFragment(BookFragment())
                    coreViewModel.setUser(UserEntity(99, password,"admin","admin","admin", "admin",0))
                }
                viewModel.login(username, password)
            }

            // Nút chuyển sang RegisterFragment
            btnSwitchToRegister.setOnClickListener {
                (activity as MainActivity).showFragment(RegisterFragment())
            }
        }
    }
}
