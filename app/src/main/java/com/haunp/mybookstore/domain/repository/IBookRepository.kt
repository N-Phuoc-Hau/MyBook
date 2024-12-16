package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.model.BookEntity
import kotlinx.coroutines.flow.Flow

interface IBookRepository {
    suspend fun getBooks() : Flow<List<BookEntity>>
    suspend fun addBook(bookEntity: BookEntity)
    suspend fun deleteBook(id: Int)
    suspend fun updateBook(bookEntity: BookEntity)
    suspend fun getBookById(id: Int): BookEntity
    fun getBookByCategoryID(categoryId: Int): Flow<List<BookEntity>>
}