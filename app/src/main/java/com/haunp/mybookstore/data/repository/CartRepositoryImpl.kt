package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.BookDao
import com.haunp.mybookstore.data.database.dao.CartDao
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.entity.CartEntity
import com.haunp.mybookstore.domain.entity.Converters
import com.haunp.mybookstore.domain.repository.ICartRepository

class CartRepositoryImpl(private val cartDao: CartDao, private val bookDao: BookDao)
    : ICartRepository {
    override fun createCart(cart: CartEntity) {
//        return cartDao.createCart(cart)
    }

//    override fun getBookInCartByUser(userId: Int): Flow<List<Int>> {
//        return cartDao.getBookInCartByUser(userId)
//    }

    override fun getCartWhenLogin(userId: Int) {
//        val cart = getCartByUserId(userId)
//        if (cart == null) {
//            val newCart = CartEntity(userId = userId, bookId = "")
//            return cartDao.createCart(newCart)
//        }
    }

    override suspend fun getCartByUserId(userId: Int): CartEntity? {
        return cartDao.getCartByUserId(userId)
    }

    override fun removeCart(cart: CartEntity) {
        return cartDao.removeCart(cart)
    }

    override suspend fun addBookToCart(userId: Int, bookId: Int) {
//         Lấy Cart hiện tại từ CartDao
        // Lấy Cart hiện tại từ CartDao
        val cart = this.getCartByUserId(userId)

        if (cart != null) {
            // Chuyển bookId từ String sang List<Int>
            val bookIds = cart.bookId.let { Converters().toBookIdList(it) } ?: emptyList()

            // Thêm bookId mới vào danh sách
            val updatedBookIds = Converters().fromBookIdList(bookIds.toMutableList().apply { add(bookId) })

            // Cập nhật lại bookId trong Cart
            cart.bookId = updatedBookIds

            // Cập nhật lại Cart trong database
            cartDao.updateCart(cart)
        } else {
            // Nếu Cart chưa tồn tại, tạo mới
            val newCart = CartEntity(
                userId = userId,
                bookId = Converters().fromBookIdList(listOf(bookId)) // Chuyển bookId thành String
            )
            // Lưu Cart mới vào database
            cartDao.createCart(newCart)
        }
    }

    override suspend fun getBookInCartByUser(userId: Int): List<BookEntity> {
        val cart = this.getCartByUserId(userId) ?: return emptyList()
        val bookIds = Converters().toBookIdList(cart.bookId) //chuyển từ kiểu String sang dạng List<Int> nhưng chưa chuyển
        return bookDao.getBooksByIds(bookIds) // Truy vấn danh sách BookEntity từ bookDao dữ liệu truyển vào là List<Int>
    }
}