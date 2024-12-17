package com.haunp.mybookstore.data.repository

import com.haunp.mybookstore.data.database.dao.RateDao
import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.domain.repository.IRateRepository

class RateRepositoryImpl(private val rateDao: RateDao): IRateRepository {
    override suspend fun addRate(rateBook: RateEntity) {
        return rateDao.insertComment(rateBook)
    }

    override suspend fun getCommentsByProductId(productId: Int): List<RateEntity> {
        return rateDao.getCommentsByProductId(productId)
    }

}