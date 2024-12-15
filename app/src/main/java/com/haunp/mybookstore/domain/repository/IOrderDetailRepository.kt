package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.entity.OrderDetailEntity

interface IOrderDetailRepository {
    suspend fun createOrderDetails(orderDetails: List<OrderDetailEntity>)
    suspend fun getOrderDetailsByOrderId(orderId: Int): List<OrderDetailEntity>
}