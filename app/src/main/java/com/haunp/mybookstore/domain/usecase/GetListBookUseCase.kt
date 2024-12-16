package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import kotlinx.coroutines.flow.Flow

class GetListBookUseCase(private val repository: IBookRepository) {
    suspend operator fun invoke(): Flow<List<BookEntity>> {
        return repository.getBooks()
    }
}