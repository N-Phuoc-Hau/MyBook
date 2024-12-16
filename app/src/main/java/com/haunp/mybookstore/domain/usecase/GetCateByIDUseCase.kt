package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.repository.ICategoryRepository

class GetCateByIDUseCase(private val categoryRepository: ICategoryRepository){
    suspend operator fun invoke(id: Int): CategoryEntity? {
        return categoryRepository.getCategoryById(id)
    }
}