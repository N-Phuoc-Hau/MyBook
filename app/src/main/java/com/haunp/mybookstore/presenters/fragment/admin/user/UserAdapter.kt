package com.haunp.mybookstore.presenters.fragment.admin.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemUserBinding
import com.haunp.mybookstore.domain.model.UserEntity

class UserAdapter(private val viewModel: UserViewModel, private val updateUser: (userId: Int) -> Unit) :RecyclerView.Adapter<UserAdapter.UserViewHolder>(){
    private val users = mutableListOf<UserEntity>()

    fun submitList(newUsers: List<UserEntity>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    inner class UserViewHolder(private val binding: ItemUserBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(user: UserEntity) {
            binding.tvName.text = user.username
            binding.tvEmail.text = user.email
            if (user.role == 0) {
                binding.tvRole.text = "Admin"
            } else {
                binding.tvRole.text = "User"
            }
            binding.imgDelete.setOnClickListener {
                viewModel.delUser(user.userId)
            }
            binding.imgUpdate.setOnClickListener {
                updateUser(user.userId)
            }
            }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(users[position])
    }

    override fun getItemCount(): Int = users.size
}