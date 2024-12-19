package com.haunp.mybookstore.presenters.fragment.user.cart

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.haunp.mybookstore.domain.usecase.CreateOrderVNPayUseCase
import org.json.JSONObject

class PaymentViewModel(private val createOrderUseCase: CreateOrderVNPayUseCase): ViewModel() {
    private val _paymentResult = MutableLiveData<String>("Đang đợi thanh toán")
    val paymentResultLiveData: MutableLiveData<String> get() = _paymentResult

    private val _orderResponse = MutableLiveData<String>()
    val orderResponseLiveData: MutableLiveData<String> get() = _orderResponse

    fun setPaymentResult(result: String) {
        _paymentResult.value = result
    }

    fun updatePaymentResult(result: String) {
        _paymentResult.value = result
    }
    suspend fun createOrder(total: String): JSONObject {
        return createOrderUseCase.invoke(total)
    }

}