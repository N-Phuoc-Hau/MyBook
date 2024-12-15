package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository

class UpdateBookUseCase(private val bookRepository: IBookRepository) {
    suspend operator fun invoke(book: BookEntity){
        return bookRepository.updateBook(book)
    }
}