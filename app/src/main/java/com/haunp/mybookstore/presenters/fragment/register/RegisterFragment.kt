package com.haunp.mybookstore.presenters.fragment.register

import android.os.Bundle
import com.haunp.mybookstore.databinding.FragmentRegisterBinding
import com.haunp.mybookstore.domain.entity.UserEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import org.koin.android.ext.android.inject

class RegisterFragment : BaseFragment<FragmentRegisterBinding>() {

    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): FragmentRegisterBinding {
        return FragmentRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initView() {
        binding {
            btnRegister.setOnClickListener {
                val userName = binding.edtUsername.text.toString()
                val password = binding.edtPassword.text.toString()
                val email = binding.edtEmail.text.toString()
                val phoneNumber = binding.edtPhone.text.toString()
                val address = binding.edtAddress.text.toString()
                val userEntity = UserEntity(
                    userId = 0,
                    username = userName,
                    password = password,
                    email = email,
                    phone = phoneNumber,
                    address = address
                )
                viewModel.registerUser(userEntity)
            }
        }
    }
}
