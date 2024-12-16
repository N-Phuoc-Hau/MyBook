package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.CartEntity
import com.haunp.mybookstore.domain.repository.ICartRepository

class GetCartByUserIdUseCase(private val cartRepo: ICartRepository) {
    suspend operator fun invoke(userId: Int):CartEntity? {
        val cart = cartRepo.getCartByUserId(userId)
        if (cart == null) {
            val newCart = CartEntity(userId = userId, bookId = "")
            cartRepo.createCart(newCart)
            return newCart
        }
        return cartRepo.getCartByUserId(userId)
    }
}