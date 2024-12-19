package com.haunp.mybookstore.domain.repository

import org.json.JSONObject

interface ICreateOrderApi {
    suspend fun createOrder(amount: String): JSONObject
}