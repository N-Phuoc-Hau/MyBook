package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository

class GetBookByIdUseCase(private val bookRepository: IBookRepository) {
    suspend operator fun invoke(id: Int): BookEntity {
        return bookRepository.getBookById(id)
    }
}