package com.haunp.mybookstore.presenters.fragment.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemOrderBinding
import com.haunp.mybookstore.domain.entity.OrderDetailEntity

class OrderDetailAdapter : RecyclerView.Adapter<OrderDetailAdapter.OrderDetailViewHolder>() {
    private val orderDetail = mutableListOf<OrderDetailEntity>() // Danh sách dữ liệu hiển thị

    fun submitList(newOrders: List<OrderDetailEntity>) {
        orderDetail.clear()
        orderDetail.addAll(newOrders)
        notifyDataSetChanged()
    }

    inner class OrderDetailViewHolder(private val binding: ItemOrderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(orderDetail: OrderDetailEntity) {
//            binding.tvPrice.text = orderDetail.totalAmount.toString()
//            binding.tvDate.text = orderDetail.orderDate.toString()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderDetailViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return OrderDetailViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderDetailViewHolder, position: Int) {
        holder.bind(orderDetail[position])
    }
    override fun getItemCount(): Int = orderDetail.size
}