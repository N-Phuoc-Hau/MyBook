package com.haunp.mybookstore.presenters.fragment.orderDetail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.model.BookEntity
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.usecase.GetBookByIdUseCase
import com.haunp.mybookstore.domain.usecase.GetOrderDetailUseCase

class OrderDetailViewModel(private val getOrderDetailUseCase: GetOrderDetailUseCase,
                            private val getBookByIdUseCase: GetBookByIdUseCase
): ViewModel() {
    private val _orderDetails = MutableLiveData<List<OrderDetailEntity>>()
    val orderDetails: LiveData<List<OrderDetailEntity>> get() = _orderDetails

    private val _books = MutableLiveData<List<BookEntity>>()
    val books: LiveData<List<BookEntity>> get() = _books

    private suspend fun setOrderDetails(orderDetails: List<OrderDetailEntity>) {
        _orderDetails.postValue(orderDetails)
    }
    suspend fun getOrderDetail(orderId: Int): List<OrderDetailEntity> {
        val orderDetail = getOrderDetailUseCase.invoke(orderId)
        setOrderDetails(orderDetail)
        Log.d("hau.np", "getOrderDetail: $orderDetail")
        return orderDetail
    }
    suspend fun addBook(bookId: Int) : List<BookEntity> {
        val book = getBookByIdUseCase.invoke(bookId)
        val currentBooks = _books.value.orEmpty().toMutableList()
        if (!currentBooks.any { it.bookId == book.bookId }) {
            currentBooks.add(book)
        }
        _books.postValue(currentBooks)
        return currentBooks // Trả về danh sách đầy đủ sau khi thêm sách
    }
}