package com.haunp.mybookstore.domain.usecase

import androidx.room.FtsOptions
import com.haunp.mybookstore.domain.entity.OrderDetailEntity
import com.haunp.mybookstore.domain.repository.IOrderDetailRepository

class AddOrderDetailUseCase(private val orderDetailRepository: IOrderDetailRepository) {
    suspend operator fun invoke(orderDetailList : List<OrderDetailEntity>){
        orderDetailRepository.createOrderDetails(orderDetailList)
    }
}