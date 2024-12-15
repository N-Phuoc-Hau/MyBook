package com.haunp.mybookstore.presenters.fragment.admin.statistical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.entity.OrderEntity
import com.haunp.mybookstore.domain.repository.IUserRepository
import com.haunp.mybookstore.domain.usecase.GetAllOrderUseCase

class StatisticalViewModel(private val getOrderUseCase: GetAllOrderUseCase,
                            private val userRepository: IUserRepository
                        ) : ViewModel() {
    private val _statistical = MutableLiveData<List<Pair<OrderEntity, String>>>()
    val statistical: LiveData<List<Pair<OrderEntity, String>>> get() = _statistical

    suspend fun getAllOrder() {
        val orders = getOrderUseCase.invoke()
        val ordersWithUsernames = orders.map { order ->
            val username = userRepository.getUserById(order.userId)?.username ?: "Unknown"
            order to username
        }
        _statistical.value = ordersWithUsernames
    }


}