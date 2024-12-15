package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.entity.CartEntity
import com.haunp.mybookstore.domain.entity.CategoryEntity
import com.haunp.mybookstore.domain.repository.ICartRepository
import com.haunp.mybookstore.domain.repository.ICategoryRepository

class GetCateByIDUseCase(private val categoryRepository: ICategoryRepository){
    suspend operator fun invoke(id: Int): CategoryEntity? {
        return categoryRepository.getCategoryById(id)
    }
}