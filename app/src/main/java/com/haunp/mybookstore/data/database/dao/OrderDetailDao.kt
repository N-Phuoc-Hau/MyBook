package com.haunp.mybookstore.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haunp.mybookstore.domain.model.OrderDetailEntity

@Dao
interface OrderDetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrderDetail(orderDetails: List<OrderDetailEntity>)

    @Query("SELECT * FROM order_items WHERE orderId = :orderId")
    suspend fun getOrderDetailsByOrderId(orderId: Int): List<OrderDetailEntity>
}