package com.haunp.mybookstore.domain.usecase

import com.haunp.mybookstore.domain.repository.ICategoryRepository

class DelCateUseCase (private val categoryRepository: ICategoryRepository){
    suspend operator fun invoke(cateId:Int){
        return categoryRepository.deleteCategory(cateId)
    }
}