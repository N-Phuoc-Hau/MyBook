package com.haunp.mybookstore.data.repository


import com.haunp.mybookstore.data.database.dao.BookDao
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import kotlinx.coroutines.flow.Flow

class BookRepositoryImpl(private var bookDao: BookDao) : IBookRepository {
    override suspend fun getBooks(): Flow<List<BookEntity>> {
        return bookDao.getAllBooks()
    }
    override suspend fun addBook(bookEntity: BookEntity) {
        return bookDao.insertBook(bookEntity)
    }

    override suspend fun deleteBook(id: Int) {
        return bookDao.deleteBookById(id)
    }
    override suspend fun updateBook(bookEntity: BookEntity) {
        return bookDao.updateBook(bookEntity)
    }

    override suspend fun getBookById(id: Int): BookEntity {
        return bookDao.getBookById(id)
    }

    override fun getBookByCategoryID(categoryId: Int): Flow<List<BookEntity>> {
        return bookDao.getBooksByCategory(categoryId)
    }


}
