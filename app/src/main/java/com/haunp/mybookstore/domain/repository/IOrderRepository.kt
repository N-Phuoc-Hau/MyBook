package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.model.OrderEntity

interface IOrderRepository {
    suspend fun insertOrder(order: OrderEntity):Long
    suspend fun getAllOrderForAdmin(): List<OrderEntity>
    suspend fun getOrdersByUserId(userId: Int): List<OrderEntity>
}