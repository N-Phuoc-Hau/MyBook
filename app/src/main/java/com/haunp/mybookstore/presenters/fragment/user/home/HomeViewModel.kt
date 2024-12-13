package com.haunp.mybookstore.presenters.fragment.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.usecase.UpdateCartUseCase
import com.haunp.mybookstore.domain.usecase.GetListBookUseCase
import kotlinx.coroutines.launch

class HomeViewModel(private val getListBookUseCase: GetListBookUseCase) : ViewModel() {
    val books: LiveData<List<BookEntity>> = liveData {
        emitSource(getListBookUseCase().asLiveData())
    }
}