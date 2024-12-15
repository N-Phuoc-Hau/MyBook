package com.haunp.mybookstore.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.haunp.mybookstore.domain.entity.OrderEntity

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrder(order: OrderEntity)

    @Query("SELECT * FROM orders WHERE userId = :userId")
    suspend fun getOrdersByUserId(userId: Int): List<OrderEntity>

    @Query("SELECT * FROM orders")
    suspend fun getAllOrders(): List<OrderEntity>

    @Update
    suspend fun updateOrder(order: OrderEntity)


}