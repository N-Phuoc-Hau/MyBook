package com.haunp.mybookstore.domain.usecase

import android.util.Log
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository

class AddBookUseCase(private val bookRepo: IBookRepository) {
    suspend operator fun invoke(book: BookEntity) {
        try{
            return bookRepo.addBook(book)
        } catch (e: Exception) {
        Log.e("AddBookUseCase", "Error adding book: ${e.message}")
    }
    }
}