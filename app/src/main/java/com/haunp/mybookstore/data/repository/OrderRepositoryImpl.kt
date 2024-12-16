package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.OrderDao
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.repository.IOrderRepository

class OrderRepositoryImpl(private val orderDao: OrderDao): IOrderRepository {
    override suspend fun insertOrder(order: OrderEntity):Long {
        return orderDao.insertOrder(order)
    }
    override suspend fun getAllOrderForAdmin():List<OrderEntity>{
        if(orderDao.getAllOrders().isEmpty()){
            return emptyList()
        }
        return orderDao.getAllOrders()
    }
    override suspend fun getOrdersByUserId(userId: Int): List<OrderEntity> {
        return orderDao.getOrdersByUserId(userId)
    }
}