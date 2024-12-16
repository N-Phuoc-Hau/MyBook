package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CartEntity

interface ICartRepository {
    fun createCart(cart:CartEntity)
//    fun getBookInCartByUser(userId:Int): Flow<List<Int>>
    fun getCartWhenLogin(userId:Int)
    suspend fun getCartByUserId(userId:Int):CartEntity?
    fun removeCart(cart:CartEntity)
    suspend fun addBookToCart(userId: Int, bookId: Int)
    suspend fun getBookInCartByUser(userId: Int): List<BookEntity>
    suspend fun clearCart(userId: Int)
    suspend fun deleteBookInCart(bookId:Int, userId: Int)
}