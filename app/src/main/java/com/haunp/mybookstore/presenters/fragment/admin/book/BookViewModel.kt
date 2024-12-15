package com.haunp.mybookstore.presenters.fragment.admin.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import com.haunp.mybookstore.domain.usecase.GetListBookUseCase
import com.haunp.mybookstore.domain.usecase.AddBookUseCase
import com.haunp.mybookstore.domain.usecase.DelBookUseCase
import com.haunp.mybookstore.domain.usecase.UpdateBookUseCase
import kotlinx.coroutines.launch

class BookViewModel(private val getListBookUseCase: GetListBookUseCase,
                    private val addBookUseCase: AddBookUseCase,
                    private val delBookUseCase: DelBookUseCase,
                    private val updateBookUseCase: UpdateBookUseCase
) : ViewModel() {
    val books : LiveData<List<BookEntity>> = liveData {
        emitSource(getListBookUseCase().asLiveData())
    }
    fun addBook(book: BookEntity) {
        viewModelScope.launch {
            Log.d("BookViewModel", "Adding book: $book")
            addBookUseCase.invoke(book)
        }
    }
    fun deleteBook(bookId: Int) {
        viewModelScope.launch {
            Log.d("BookViewModel", "Deleting book with ID: $bookId")
            delBookUseCase.invoke(bookId)
        }
    }
    fun updateBook(book: BookEntity) {
        viewModelScope.launch {
            Log.d("BookViewModel", "Updating book: $book")
            updateBookUseCase.invoke(book)
        }
    }
}