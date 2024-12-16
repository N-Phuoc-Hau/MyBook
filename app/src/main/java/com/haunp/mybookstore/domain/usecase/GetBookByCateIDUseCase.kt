package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import kotlinx.coroutines.flow.Flow

class GetBookByCateIDUseCase(private val bookRepo: IBookRepository) {
    operator fun invoke(categoryId: Int): Flow<List<BookEntity>> {
        return bookRepo.getBookByCategoryID(categoryId)
    }
}