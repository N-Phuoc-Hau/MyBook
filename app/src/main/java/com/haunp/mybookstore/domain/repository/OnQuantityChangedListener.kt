package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.domain.model.BookEntity

interface OnQuantityChangedListener {
    fun onQuantityChanged(bookId: Int, quantity: Int, totalPrice: Double)
}