package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.entity.OrderEntity

interface IOrderRepository {
    suspend fun insertOrder(order: OrderEntity)
    suspend fun getAllOrderForAdmin(): List<OrderEntity>
    suspend fun getOrdersByUserId(userId: Int): List<OrderEntity>
}