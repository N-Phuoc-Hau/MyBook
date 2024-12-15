package com.haunp.mybookstore.presenters.fragment.user

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.entity.OrderDetailEntity
import com.haunp.mybookstore.domain.usecase.GetOrderDetailUseCase

class OrderDetailViewModel(private val getOrderDetailUseCase: GetOrderDetailUseCase): ViewModel() {
    private val _orderDetails = MutableLiveData<List<OrderDetailEntity>>()
    val orderDetails: LiveData<List<OrderDetailEntity>> get() = _orderDetails

    fun setOrderDetails(orderDetails: List<OrderDetailEntity>) {
        _orderDetails.value = orderDetails
    }
    suspend fun getOrderDetail(orderId: Int) {
        val orderDetail = getOrderDetailUseCase.invoke(orderId)
        if(orderDetail.isEmpty()){
            _orderDetails.value = emptyList()
        }else{
            _orderDetails.value = orderDetail
        }
    }

}