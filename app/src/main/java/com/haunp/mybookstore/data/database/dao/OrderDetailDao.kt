package com.haunp.mybookstore.data.database.dao

import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haunp.mybookstore.domain.entity.OrderDetailEntity

interface OrderDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(orderDetails: List<OrderDetailEntity>)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderDetailsByOrderId(orderId: Int): List<OrderDetailEntity>


}