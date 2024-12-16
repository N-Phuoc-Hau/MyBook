package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.repository.IOrderDetailRepository

class AddOrderDetailUseCase(private val orderDetailRepository: IOrderDetailRepository) {
    suspend operator fun invoke(orderDetailList : List<OrderDetailEntity>){
        orderDetailRepository.createOrderDetails(orderDetailList)
    }
}