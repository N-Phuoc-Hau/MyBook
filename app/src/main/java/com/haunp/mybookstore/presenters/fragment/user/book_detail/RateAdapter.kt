package com.haunp.mybookstore.presenters.fragment.user.book_detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.haunp.mybookstore.databinding.ItemRateBinding
import com.haunp.mybookstore.domain.model.RateEntity

class RateAdapter : RecyclerView.Adapter<RateAdapter.RateViewHolder>(){
    private val rates = mutableListOf<RateEntity>()

    fun submitList(newRates: List<RateEntity>) {
        rates.clear()
        rates.addAll(newRates)
        notifyDataSetChanged()
    }

    inner class RateViewHolder(private val binding: ItemRateBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(rate: RateEntity) {
                binding.tvUsername.text = rate.userName
                binding.tvComment.text = rate.comment
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateViewHolder {
        val binding = ItemRateBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)
        return RateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RateViewHolder, position: Int) {
        holder.bind(rates[position])
    }

    override fun getItemCount(): Int = rates.size


}