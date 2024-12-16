package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.repository.ICartRepository

class DelBookInCartUseCase(private val cartRepository: ICartRepository) {
    suspend operator fun invoke(bookId: Int, userId: Int) {
        cartRepository.deleteBookInCart(bookId, userId)
    }

}