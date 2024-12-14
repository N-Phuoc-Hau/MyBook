package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.repository.ICartRepository

class UpdateCartUseCase(private val cartRepo: ICartRepository) {
    suspend operator fun invoke(userId: Int, bookId: Int){
        return cartRepo.addBookToCart(userId, bookId)
    }
    suspend fun clearCart(userId: Int){
        return cartRepo.clearCart(userId)
    }


}