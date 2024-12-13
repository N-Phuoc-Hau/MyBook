package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.data.database.dao.CartDao
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.entity.CartEntity
import kotlinx.coroutines.flow.Flow

interface ICartRepository {
    fun createCart(cart:CartEntity)
//    fun getBookInCartByUser(userId:Int): Flow<List<Int>>
    fun getCartWhenLogin(userId:Int)
    suspend fun getCartByUserId(userId:Int):CartEntity?
    fun removeCart(cart:CartEntity)
    suspend fun addBookToCart(userId: Int, bookId: Int)
    suspend fun getBookInCartByUser(userId: Int): List<BookEntity>

}