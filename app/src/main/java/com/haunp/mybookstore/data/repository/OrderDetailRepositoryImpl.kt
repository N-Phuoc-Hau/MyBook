package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.OrderDetailDao
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.repository.IOrderDetailRepository

class OrderDetailRepositoryImpl(private val orderDetailDao: OrderDetailDao) : IOrderDetailRepository {
    override suspend fun createOrderDetails(orderDetails: List<OrderDetailEntity>) {
        return orderDetailDao.insertOrderDetail(orderDetails)
    }

    override suspend fun getOrderDetailsByOrderId(orderId: Int): List<OrderDetailEntity> {
        return orderDetailDao.getOrderDetailsByOrderId(orderId)
    }
}