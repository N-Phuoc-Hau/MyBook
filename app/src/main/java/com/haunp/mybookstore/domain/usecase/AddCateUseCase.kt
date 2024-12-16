package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.model.CategoryEntity
import com.haunp.mybookstore.domain.repository.ICategoryRepository


class AddCateUseCase(private val categoryRepository: ICategoryRepository) {
    suspend operator fun invoke(category: CategoryEntity) {
        return categoryRepository.addCategory(category)
    }
}