package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.entity.OrderEntity
import com.haunp.mybookstore.domain.repository.IOrderRepository

class AddOrderUseCase(private val orderRepository: IOrderRepository) {
    suspend operator fun invoke(order: OrderEntity){
        orderRepository.insertOrder(order)
    }
}