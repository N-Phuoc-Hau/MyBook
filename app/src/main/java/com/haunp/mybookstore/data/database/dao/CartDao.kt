package com.haunp.mybookstore.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.haunp.mybookstore.domain.model.CartEntity

@Dao
interface CartDao {
    // Thêm giỏ hàng mới hoặc cập nhật nếu đã tồn tại
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun createCart(cart: CartEntity)

    @Query("SELECT * FROM carts WHERE userid = :userId")
    suspend fun getCartByUserId(userId: Int): CartEntity?

    @Delete
    fun removeCart(cart: CartEntity)

    @Query("DELETE FROM carts WHERE userid = :userId")
    suspend fun clearCart(userId: Int)

    @Update
    fun updateCart(cart: CartEntity)


}
