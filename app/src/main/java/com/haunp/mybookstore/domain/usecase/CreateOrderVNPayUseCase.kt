package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.repository.ICreateOrderApi
import org.json.JSONObject

class CreateOrderVNPayUseCase(private val createOderRepository: ICreateOrderApi) {
    suspend operator fun invoke(amount: String): JSONObject{
        return createOderRepository.createOrder(amount)
    }
}