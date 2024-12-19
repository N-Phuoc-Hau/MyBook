package com.haunp.mybookstore.data.repository

import android.util.Log
import androidx.room.Dao
import androidx.room.Entity
import com.haunp.mybookstore.data.api.HttpProvider
import com.haunp.mybookstore.data.database.dao.ZaloPayDao
import com.haunp.mybookstore.domain.model.ZaloPayOrder
import com.haunp.mybookstore.domain.constant.AppInfo
import com.haunp.mybookstore.domain.repository.ICreateOrderApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.FormBody
import okhttp3.RequestBody
import org.json.JSONObject

class CreateOrder(private val zaloPayDao: ZaloPayDao) : ICreateOrderApi {

    override suspend fun createOrder(amount: String): JSONObject {
        return withContext(Dispatchers.IO) {
            try {
                val input = ZaloPayOrder(amount = amount)

                val formBody: RequestBody = FormBody.Builder()
                    .add("app_id", input.appId)
                    .add("app_user", input.appUser)
                    .add("app_time", input.appTime)
                    .add("amount", input.amount)
                    .add("app_trans_id", input.appTransId)
                    .add("embed_data", input.embedData)
                    .add("item", input.items)
                    .add("bank_code", input.bankCode)
                    .add("description", input.description)
                    .add("mac", input.mac)
                    .build()

                // Gửi request POST
                val response = HttpProvider.sendPost(url = AppInfo.URL_CREATE_ORDER, formBody = formBody)

                // Lưu thông tin đơn hàng vào Room
                zaloPayDao.insertPayment(input)
                Log.d("hau.np","createOrder: $response")
                // Trả về kết quả
                response
            } catch (e: Exception) {
                throw Exception("Failed to create order: ${e.message}")
            }!!
        }
    }
}
