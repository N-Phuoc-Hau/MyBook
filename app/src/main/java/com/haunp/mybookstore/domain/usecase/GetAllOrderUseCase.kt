package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.repository.IOrderRepository

class GetAllOrderUseCase(private val orderRepository: IOrderRepository) {
    suspend operator fun invoke(): List<OrderEntity> {
        return orderRepository.getAllOrderForAdmin()
    }
}