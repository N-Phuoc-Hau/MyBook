package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.domain.repository.IRateRepository

class GetListRateUseCase(private val rateRepository: IRateRepository) {
    suspend operator fun invoke(productId: Int): List<RateEntity> {
        return rateRepository.getCommentsByProductId(productId)
    }
}