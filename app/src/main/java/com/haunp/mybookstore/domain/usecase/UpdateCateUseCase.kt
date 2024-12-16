package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.repository.ICategoryRepository

class UpdateCateUseCase(private val categoryRepository: ICategoryRepository) {
    suspend operator fun invoke(cate: CategoryEntity){
        return categoryRepository.updateCategory(cate)
    }
}