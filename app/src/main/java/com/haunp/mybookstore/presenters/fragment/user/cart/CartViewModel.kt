package com.haunp.mybookstore.presenters.fragment.user.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.entity.BookEntity
import com.haunp.mybookstore.domain.entity.CartEntity
import com.haunp.mybookstore.domain.repository.IBookRepository
import com.haunp.mybookstore.domain.usecase.GetBookInCartUserCase
import com.haunp.mybookstore.domain.usecase.GetCartByUserIdUseCase
import com.haunp.mybookstore.domain.usecase.UpdateCartUseCase
import kotlinx.coroutines.launch

class CartViewModel(
    private val getCartByUserIdUseCase: GetCartByUserIdUseCase,
    private val getBookInCartUserCase: GetBookInCartUserCase,
    private val updateCartUseCase: UpdateCartUseCase,
    private val bookRepo: IBookRepository
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


    fun getBookInCart(userId: Int){
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
}
