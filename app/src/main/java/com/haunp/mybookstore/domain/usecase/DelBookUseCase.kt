package com.haunp.mybookstore.domain.usecase

import android.widget.Toast
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository

class DelBookUseCase (private val bookRepository: IBookRepository) {
    suspend operator fun invoke(bookId:Int){
        bookRepository.deleteBook(bookId)
    }
}