package com.haunp.mybookstore.presenters.fragment.admin.user

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.haunp.mybookstore.databinding.UserFragmentBinding
import com.haunp.mybookstore.domain.model.UserEntity
import com.haunp.mybookstore.presenters.base.BaseFragment
import org.koin.android.ext.android.inject

class UserFragment : BaseFragment<UserFragmentBinding>(){
    private val viewModel: UserViewModel by inject()
    override var isTerminalBackKeyActive: Boolean = true

    override fun getDataBinding(): UserFragmentBinding{
        return UserFragmentBinding.inflate(layoutInflater)
    }
    override fun initView() {
        val adapter = UserAdapter()
        binding.userRecyclerView.adapter = adapter
        binding.userRecyclerView.layoutManager = LinearLayoutManager(context)
        viewModel.users.observe(viewLifecycleOwner) { userList ->
            adapter.submitList(userList)
        }
    }
    override fun initAction() {
        binding{
            btnAdd.setOnClickListener{
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val phone = edtPhone.text.toString()
                val address = edtAddress.text.toString()
                val role = edtRole.text.toString()
                val userEntity = UserEntity(
                    username = name,
                    email = email,
                    password = password,
                    phone = phone,
                    address = address,
                    role = role.toInt()
                )
                viewModel.registerUser(userEntity)
                clearText()
            }
            btnDel.setOnClickListener{
                val id = edtID.text.toString().trim()
                val users = viewModel.users.value ?: emptyList()
                for (user in users) {
                    if (user.userId == id.toInt()) {
                        viewModel.delUser(id.toInt())
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show()
                        clearText()
                        return@setOnClickListener
                    }
                }
                Toast.makeText(context, "Không tìm thấy sách với ID này", Toast.LENGTH_SHORT).show()
                clearText()
            }
            btnUpdate.setOnClickListener{
                val id = edtID.text.toString().trim()
                val name = edtName.text.toString()
                val email = edtEmail.text.toString()
                val password = edtPassword.text.toString()
                val phone = edtPhone.text.toString()
                val address = edtAddress.text.toString()
                val role = edtRole.text.toString()
                val userEntity = UserEntity(
                    userId = id.toInt(),
                    username = name,
                    email = email,
                    password = password,
                    phone = phone,
                    address = address,
                    role = role.toInt()
                )
                val users = viewModel.users.value ?: emptyList()
                for (user in users) {
                    if (user.userId == id.toInt()) {
                        viewModel.updateUser(userEntity)
                        Toast.makeText(context, "Update thành công", Toast.LENGTH_SHORT).show()
                        clearText()
                        return@setOnClickListener
                    }
                }
                Toast.makeText(context, "Không tìm thấy sách với ID này", Toast.LENGTH_SHORT).show()
                clearText()
            }
        }
    }
    private fun clearText() {
        binding{
            edtName.text.clear()
            edtEmail.text.clear()
            edtPassword.text.clear()
            edtPhone.text.clear()
            edtAddress.text.clear()
            edtID.text.clear()
        }
    }


}