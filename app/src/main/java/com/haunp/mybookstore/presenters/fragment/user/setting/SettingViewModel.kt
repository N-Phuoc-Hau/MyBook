package com.haunp.mybookstore.presenters.fragment.user.setting

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.entity.OrderEntity
import com.haunp.mybookstore.domain.entity.UserEntity
import com.haunp.mybookstore.domain.usecase.AddOrderUseCase
import com.haunp.mybookstore.domain.usecase.GetOrderByUserUseCase

class SettingViewModel(private val getOrder: GetOrderByUserUseCase,
                       private val addOrderUseCase: AddOrderUseCase) : ViewModel() {
    private val _orders = MutableLiveData<List<OrderEntity>>()
    val orders: LiveData<List<OrderEntity>> get() = _orders
    suspend fun getOrder(userId: Int) {
        val order = getOrder.invoke(userId)
        if(order.isEmpty()){
            _orders.value = emptyList()
        }else{
            _orders.value = order
        }
    }
    suspend fun insertOrder(order: OrderEntity, userId: Int){
        getOrder(userId)
        addOrderUseCase.invoke(order)
    }
}