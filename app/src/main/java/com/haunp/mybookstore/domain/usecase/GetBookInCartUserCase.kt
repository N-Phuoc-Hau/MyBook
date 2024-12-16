package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.ICartRepository

class GetBookInCartUserCase(private val cartRepo: ICartRepository) {
    suspend operator fun invoke(userId: Int): List<BookEntity> {
        return cartRepo.getBookInCartByUser(userId)
    }
}