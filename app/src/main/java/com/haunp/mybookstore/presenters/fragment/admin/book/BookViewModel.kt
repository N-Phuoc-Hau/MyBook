package com.haunp.mybookstore.presenters.fragment.admin.book

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.usecase.GetListBookUseCase
import com.haunp.mybookstore.domain.usecase.AddBookUseCase
import com.haunp.mybookstore.domain.usecase.DelBookUseCase
import com.haunp.mybookstore.domain.usecase.GetCateUseCase
import com.haunp.mybookstore.domain.usecase.UpdateBookUseCase
import kotlinx.coroutines.launch

class BookViewModel(private val getListBookUseCase: GetListBookUseCase,
                    private val addBookUseCase: AddBookUseCase,
                    private val delBookUseCase: DelBookUseCase,
                    private val updateBookUseCase: UpdateBookUseCase,
                    private val getCateUseCase: GetCateUseCase
) : ViewModel() {
    val books : LiveData<List<BookEntity>> = liveData {
        emitSource(getListBookUseCase().asLiveData())
    }
    private val _categories = MutableLiveData<List<CategoryEntity>>()
    val categories: LiveData<List<CategoryEntity>> = _categories
    init {
        viewModelScope.launch {
            getCateUseCase().collect { categoriesList ->
                _categories.postValue(categoriesList)
                Log.d("hau.np", "Categories: $categoriesList")
            }
        }
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
    fun isCategoryExist(cateName: String): Boolean {
        val categoryIds = categories.value?.map { it.name }
        Log.d("hau.np", "Category IDs: $categoryIds")
        return categoryIds?.contains(cateName) ?: false
    }
    fun getCateID(cateName: String): Int? {
        val category = categories.value?.find { it.name == cateName }
        return category?.categoryId
    }
}