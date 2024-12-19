package com.haunp.mybookstore.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.haunp.mybookstore.domain.model.ZaloPayOrder

@Dao
interface ZaloPayDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPayment(payment: ZaloPayOrder)

    @Query("SELECT * FROM ZaloPayOrder WHERE appTransId = :transactionId")
    suspend fun getPaymentByTransactionId(transactionId: String): ZaloPayOrder?
}