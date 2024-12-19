package com.haunp.mybookstore.presenters.fragment.user.setting

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemOrderBinding
import com.haunp.mybookstore.domain.model.OrderEntity
import java.text.NumberFormat
import java.util.Locale

class OrderAdapter : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {
    private val orders = mutableListOf<OrderEntity>() // Danh sách dữ liệu hiển thị
    var onItemClick : (OrderEntity) -> Unit = {}
    fun submitList(newOrders: List<OrderEntity>) {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }

    inner class OrderViewHolder(private val binding: ItemOrderBinding) :
            RecyclerView.ViewHolder(binding.root) {
        fun bind(order: OrderEntity) {
            val formattedPrice = NumberFormat.getNumberInstance(Locale("vi", "VN"))
                .format(order.totalAmount)
            binding.tvPrice.text = "$formattedPrice đ"
            binding.tvDate.text = order.orderDate.toString()
            binding.root.setOnClickListener{
                onItemClick(order)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }
    override fun getItemCount(): Int = orders.size
}