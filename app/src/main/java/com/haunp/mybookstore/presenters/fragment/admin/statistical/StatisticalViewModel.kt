package com.haunp.mybookstore.presenters.fragment.admin.statistical

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.haunp.mybookstore.domain.model.OrderEntity
import com.haunp.mybookstore.domain.repository.IUserRepository
import com.haunp.mybookstore.domain.usecase.GetAllOrderUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class StatisticalViewModel(private val getOrderUseCase: GetAllOrderUseCase,
                            private val userRepository: IUserRepository
                        ) : ViewModel() {
    private val _statistical = MutableLiveData<List<Pair<OrderEntity, String>>>()
    val statistical: LiveData<List<Pair<OrderEntity, String>>> get() = _statistical

    suspend fun getAllOrder() {
        viewModelScope.launch(Dispatchers.IO) {
            val orders = getOrderUseCase.invoke()
            val ordersWithUsernames = orders.map { order ->
                val username = userRepository.getUserById(order.userId)?.username ?: "Unknown"
                order to username
            }
            withContext(Dispatchers.Main) {
                _statistical.value = ordersWithUsernames
            }
        }
    }


}