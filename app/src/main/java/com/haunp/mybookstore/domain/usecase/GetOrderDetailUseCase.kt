package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.repository.IOrderDetailRepository

class GetOrderDetailUseCase(private val orderDetailRepository: IOrderDetailRepository) {
    suspend operator fun invoke(orderId: Int): List<OrderDetailEntity> {
        return orderDetailRepository.getOrderDetailsByOrderId(orderId)

    }
}