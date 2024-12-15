package com.haunp.mybookstore.presenters.fragment.admin.statistical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemStatisticalBinding
import com.haunp.mybookstore.domain.entity.OrderEntity
import com.haunp.mybookstore.domain.repository.IUserRepository
import org.koin.java.KoinJavaComponent.inject

class StatisticalAdapter() : RecyclerView.Adapter<StatisticalAdapter.StatisticalViewHolder>() {
    private val orders = mutableListOf<Pair<OrderEntity, String>>() // Danh sách dữ liệu hiển thị
    fun submitList(newOrders: List<Pair<OrderEntity, String>>) {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }
    inner class StatisticalViewHolder(private val binding: ItemStatisticalBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(orderWithUser: Pair<OrderEntity, String>) {
            val (order, username) = orderWithUser
            binding.tvNameUser.text = username
            binding.tvNameUser.text = username.toString()
            binding.tvPrice.text = order.totalAmount.toString()
            binding.tvDate.text = order.orderDate.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatisticalViewHolder {
        val binding = ItemStatisticalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatisticalViewHolder(binding)
    }

    override fun onBindViewHolder(holder: StatisticalViewHolder, position: Int) {
        holder.bind(orders[position])
    }
    override fun getItemCount(): Int = orders.size
}