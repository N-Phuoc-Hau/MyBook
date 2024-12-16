package com.haunp.mybookstore.presenters.fragment.user.category_user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.usecase.GetBookByCateIDUseCase
import com.haunp.mybookstore.domain.usecase.GetCateUseCase
import kotlinx.coroutines.launch

class CategoryUserViewModel(private var getCateUseCase:GetCateUseCase, private var getBookByCateIDUseCase: GetBookByCateIDUseCase) : ViewModel() {
    val categories : LiveData<List<CategoryEntity>> = liveData {
        emitSource(getCateUseCase().asLiveData())
    }
    private val _booksByCategory = MutableLiveData<List<BookEntity>>()
    val booksByCategory: LiveData<List<BookEntity>> get() = _booksByCategory

    fun getBookByCateID(categoryId: Int) {
        viewModelScope.launch {
            getBookByCateIDUseCase.invoke(categoryId).collect{
                books ->
                _booksByCategory.postValue(books)

            }
        }
    }


}