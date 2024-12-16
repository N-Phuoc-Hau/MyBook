package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.BookDao
import com.haunp.mybookstore.data.database.dao.CartDao
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CartEntity
import com.haunp.mybookstore.domain.model.Converters
import com.haunp.mybookstore.domain.repository.ICartRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

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
        withContext(Dispatchers.IO) {
            val cart = cartDao.getCartByUserId(userId)
            if (cart != null) {
                // Kiểm tra nếu bookId là chuỗi trống trước khi chuyển đổi
                val bookIds = if (cart.bookId.isNotEmpty()) {
                    Converters().toBookIdList(cart.bookId)
                } else {
                    emptyList()
                }

                if (bookId in bookIds) {
                    return@withContext // Dừng hàm nếu sách đã tồn tại
                }

                // Thêm bookId mới vào danh sách
                val updatedBookIds =
                    Converters().fromBookIdList(bookIds.toMutableList().apply { add(bookId) })
                cart.bookId = updatedBookIds

                // Cập nhật lại Cart trong database
                cartDao.updateCart(cart)
            } else {
                val newCart = CartEntity(
                    userId = userId,
                    bookId = Converters().fromBookIdList(listOf(bookId)) // Chuyển bookId thành String
                )
                cartDao.createCart(newCart)
            }
        }
    }


    override suspend fun getBookInCartByUser(userId: Int): List<BookEntity> {
        return withContext(Dispatchers.IO) {
            val cart = getCartByUserId(userId) ?: return@withContext emptyList()
            // Kiểm tra chuỗi bookId có hợp lệ hay không
            val bookIds = if (cart.bookId.isNotEmpty()) {
                Converters().toBookIdList(cart.bookId) // Chuyển từ String sang List<Int>
            } else {
                emptyList() // Nếu chuỗi trống, trả về danh sách trống
            }
            // Truy vấn sách từ bookDao
            bookDao.getBooksByIds(bookIds)
        }
    }
    override suspend fun clearCart(userId: Int) {
        cartDao.clearCart(userId)
    }

    override suspend fun deleteBookInCart(bookId: Int, userId: Int) {
        withContext(Dispatchers.IO) {
            val cart = cartDao.getCartByUserId(userId)
            if (cart != null) {
                // Chuyển đổi bookId từ chuỗi thành danh sách nếu cần
                val bookIds = cart.bookId.let { Converters().toBookIdList(it) } ?: emptyList()
                if (bookId in bookIds) {
                    val updatedBookIds = bookIds.toMutableList().apply { remove(bookId) }

                    // Cập nhật lại danh sách bookId trong cart
                    cart.bookId = Converters().fromBookIdList(updatedBookIds)

                    // Cập nhật lại Cart trong database
                    cartDao.updateCart(cart)
                }
            }
            if (cartDao.getCartByUserId(userId)?.bookId.isNullOrEmpty()) {
                cartDao.clearCart(userId)
            }
        }
    }
}