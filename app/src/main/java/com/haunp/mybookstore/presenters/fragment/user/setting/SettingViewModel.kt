package com.haunp.mybookstore.presenters.fragment.user.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.model.OrderDetailEntity
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.usecase.AddOrderDetailUseCase
import com.haunp.mybookstore.domain.usecase.AddOrderUseCase
import com.haunp.mybookstore.domain.usecase.GetOrderByUserUseCase

class SettingViewModel(private val getOrder: GetOrderByUserUseCase,
                       private val addOrderUseCase: AddOrderUseCase,
                        private val addOrderDetailUseCase: AddOrderDetailUseCase
) : ViewModel() {
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
    suspend fun insertOrder(order: OrderEntity):Long{
        return addOrderUseCase.invoke(order)
    }
    suspend fun insertOrderDetails(orderDetails: List<OrderDetailEntity>){
        addOrderDetailUseCase.invoke(orderDetails)
    }

}