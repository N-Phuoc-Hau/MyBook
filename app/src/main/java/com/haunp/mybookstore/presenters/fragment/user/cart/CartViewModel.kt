package com.haunp.mybookstore.presenters.fragment.user.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.CartEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import com.haunp.mybookstore.domain.usecase.DelBookInCartUseCase
import com.haunp.mybookstore.domain.usecase.GetBookInCartUserCase
import com.haunp.mybookstore.domain.usecase.GetCartByUserIdUseCase
import com.haunp.mybookstore.domain.usecase.UpdateCartUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val getBookInCartUserCase: GetBookInCartUserCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val deleteBookInCart: DelBookInCartUseCase,
    private val bookRepo: IBookRepository,
) : ViewModel() {
    private val _cart = MutableLiveData<CartEntity?>()
    val cart: MutableLiveData<CartEntity?> get() = _cart
    private val _bookInCart = MutableLiveData<List<BookEntity>>()
    val bookInCart: MutableLiveData<List<BookEntity>> get() = _bookInCart

    fun addBookToCart(userId: Int, bookId: Int) {
        viewModelScope.launch {
            updateCartUseCase.invoke(userId, bookId)
            val listBook = getBookInCartUserCase.invoke(userId)
            _bookInCart.value = listBook
        }
    }

    fun deleteBookInCart(bookId: Int, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteBookInCart.invoke(bookId, userId)
            Log.d("hau.np", "deleteBookInCart: $bookId")
            val listBook = getBookInCartUserCase.invoke(userId)
            Log.d("hau.np", "deleteBookInCart: $listBook")
            _bookInCart.postValue(listBook)
        }
    }

    fun getBookInCart(userId: Int) {
        viewModelScope.launch {
            val listBook = getBookInCartUserCase.invoke(userId)
            _bookInCart.value = listBook
            Log.d("hau.np", "getBookInCart: $listBook")
        }
    }

    fun getCartByUserId(userId: Int) {
        viewModelScope.launch {
            val cart = getCartByUserIdUseCase.invoke(userId)
            Log.d("hau.np", "getCartByUserId: $cart")
            _cart.value = cart
        }
    }

    fun clearCart(userId: Int) {
        viewModelScope.launch {
            updateCartUseCase.clearCart(userId)
            val listBook = getBookInCartUserCase.invoke(userId)
            _bookInCart.value = listBook
        }
    }
}
