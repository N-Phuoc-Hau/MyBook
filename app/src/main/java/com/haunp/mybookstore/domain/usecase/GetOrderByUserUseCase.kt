package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.repository.IOrderRepository

class GetOrderByUserUseCase(private val orderRepo: IOrderRepository) {
    suspend operator fun invoke(userId: Int): List<OrderEntity> {
        return orderRepo.getOrdersByUserId(userId)
    }
}