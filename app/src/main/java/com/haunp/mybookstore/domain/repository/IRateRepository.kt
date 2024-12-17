package com.haunp.mybookstore.domain.repository

import com.haunp.mybookstore.data.database.dao.RateDao
import com.haunp.mybookstore.domain.model.RateEntity

interface IRateRepository{
    suspend fun addRate(rateBook: RateEntity)
    suspend fun getCommentsByProductId(productId: Int): List<RateEntity>
}