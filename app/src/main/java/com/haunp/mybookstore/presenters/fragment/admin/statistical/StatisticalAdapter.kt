package com.haunp.mybookstore.presenters.fragment.admin.statistical

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemStatisticalBinding
import com.haunp.mybookstore.domain.model.OrderEntity

class StatisticalAdapter() : RecyclerView.Adapter<StatisticalAdapter.StatisticalViewHolder>() {
    private val orders = mutableListOf<Pair<OrderEntity, String>>() // Danh sách dữ liệu hiển thị
    var onItemClick : (OrderEntity) -> Unit = {}
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
            binding.tvPrice.text = order.totalAmount.toString()
            binding.tvDate.text = order.orderDate
            binding.root.setOnClickListener{
                onItemClick(order)
            }
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