package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.RateEntity
import com.haunp.mybookstore.domain.repository.IRateRepository

class AddRateUseCase(private val rateRepository: IRateRepository) {
    suspend operator fun invoke(rateBook: RateEntity) {
        rateRepository.addRate(rateBook)
    }
}